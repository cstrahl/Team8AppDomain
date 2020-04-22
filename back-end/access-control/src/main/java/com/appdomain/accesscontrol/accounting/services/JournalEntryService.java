package com.appdomain.accesscontrol.accounting.services;

import com.appdomain.accesscontrol.accounting.contracts.JournalEntryDto;
import com.appdomain.accesscontrol.accounting.contracts.JournalEntryRequestDto;
import com.appdomain.accesscontrol.accounting.contracts.JournalEntryReviewDto;
import com.appdomain.accesscontrol.accounting.contracts.LedgerEntryDto;
import com.appdomain.accesscontrol.accounting.domains.Journal_Entry;
import com.appdomain.accesscontrol.accounting.repositories.JournalEntryRepository;
import com.appdomain.accesscontrol.accounting.utils.JournalEntryStatus;
import com.appdomain.accesscontrol.security.contracts.CustomUserDetails;
import com.appdomain.accesscontrol.security.services.CustomUserDetailsService;
import com.appdomain.accesscontrol.security.utils.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.appdomain.accesscontrol.accounting.utils.JournalEntryStatus.*;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;

    private final JournalService journalService;
    private final LedgerEntryService ledgerEntryService;
    private final AccountService accountService;
    private final CustomUserDetailsService userDetailsService;

    public JournalEntryService(final JournalEntryRepository journalEntryRepository,
                               final JournalService journalService,
                               final LedgerEntryService ledgerEntryService,
                               final AccountService accountService,
                               final CustomUserDetailsService userDetailsService) {
        this.journalEntryRepository = journalEntryRepository;
        this.journalService = journalService;
        this.ledgerEntryService = ledgerEntryService;
        this.accountService = accountService;
        this.userDetailsService = userDetailsService;
    }

    public List<JournalEntryDto> getByFilters(final long journalId,
                                              Set<JournalEntryStatus> filters) {
        final Long userId = getCurrentUserId();
        if (CollectionUtils.isEmpty(filters)) {
            filters = Set.of(CREATED, PENDING, APPROVED, DENIED);
        }
        return this.journalEntryRepository.findAllByJournalIdAndStatusIn(journalId,
                filters.stream().map(Enum::name).collect(Collectors.toSet()))
                .stream()
                .filter(journalEntry -> !journalEntry.getStatus().equals(CREATED.name())
                        || journalEntry.getCreatorId().equals(userId)
                        || journalEntry.getReviewerId().equals(userId))
                .map(JournalEntryDto::new)
                .collect(Collectors.toList());
    }

    public JournalEntryDto getById(long id) {
        return new JournalEntryDto(this.journalEntryRepository.getOne(id));
    }

    public List<JournalEntryDto> getAllEntries(long journalId) {
        return this.journalEntryRepository.findAllByJournalId(journalId)
                .stream()
                .map(JournalEntryDto::new)
                .collect(Collectors.toList());
    }

    public JournalEntryRequestDto createRequest(final JournalEntryRequestDto journalEntryRequestDto) {
        final Journal_Entry journalEntry = this.getJournalEntry(journalEntryRequestDto.getJournalEntryDto());
        final List<LedgerEntryDto> ledgerEntryDtos = journalEntryRequestDto.getLedgerEntryDtos();
        this.verifyAccountBalances(ledgerEntryDtos);

        this.journalEntryRepository.save(journalEntry);
        return new JournalEntryRequestDto(new JournalEntryDto(journalEntry),
                this.ledgerEntryService.createAndSaveAll(ledgerEntryDtos, journalEntry.getId()).stream()
                        .map(LedgerEntryDto::new)
                        .collect(Collectors.toList()));
    }

    public JournalEntryDto reviewRequest(final JournalEntryReviewDto reviewDto) {
        final Journal_Entry journalEntry = this.journalEntryRepository.findById(reviewDto.getJournalEntryDto().getId())
                .orElseThrow(() -> new RuntimeException("Could not find request"));
        this.verifyStatus(journalEntry);

        if (reviewDto.isApproved()) {
            journalEntry.setStatus(APPROVED.name());
            this.ledgerEntryService.acceptAllForJournalEntry(journalEntry.getId());
        } else {
            journalEntry.setStatus(DENIED.name());
            journalEntry.setDeniedReason(reviewDto.getReason());
        }
        journalEntry.setReviewerId(this.getCurrentUserId());
        journalEntry.setReviewDate(Instant.now());
        return new JournalEntryDto(this.journalEntryRepository.save(journalEntry));
    }

    private Journal_Entry getJournalEntry(final JournalEntryDto journalEntryDto) {
        final Long userId = this.getCurrentUserId();
        if (Optional.ofNullable(journalEntryDto).isPresent()
                && Optional.ofNullable(journalEntryDto.getId()).isPresent()) {
            final Optional<Journal_Entry> optional = this.journalEntryRepository.findById(
                    journalEntryDto.getId());
            if (optional.isPresent()) {
                final Journal_Entry journalEntry = optional.get();
                if (!journalEntry.getCreatorId().equals(userId)) {
                    throw new RuntimeException("Cannot edit someone else's request");
                }
                this.verifyStatus(journalEntry);
                journalEntry.setStatus(journalEntryDto.getStatus().name());
                return this.journalEntryRepository.save(journalEntry);
            }
        }
        return this.journalEntryRepository.save(new Journal_Entry(
                this.journalService.getCurrentJournal().getId(), userId,
                null, null, null, CREATED.name(), null));
    }

    private void verifyStatus(Journal_Entry journalEntry) {
        if (!journalEntry.getStatus().equals(CREATED.name())
                && !journalEntry.getStatus().equals(PENDING.name())) {
            throw new RuntimeException("Cannot update closed request");
        }
    }

    private void verifyAccountBalances(final List<LedgerEntryDto> ledgerEntryDtos) {
        final Map<Long, Double> accountBalances = new HashMap<>();
        for (LedgerEntryDto ledgerEntryDto : ledgerEntryDtos) {
            final long accountId = ledgerEntryDto.getAccountId();
            if (ledgerEntryDto.getCredit() != 0.0) {
                accountBalances.put(accountId,
                        accountBalances.getOrDefault(accountId, 0.0) - ledgerEntryDto.getCredit());
                continue;
            } else if (ledgerEntryDto.getDebit() != 0.0) {
                accountBalances.put(accountId,
                        accountBalances.getOrDefault(accountId, 0.0) + ledgerEntryDto.getDebit());
                continue;
            }
            throw new RuntimeException("No Value");
        }
        //TODO: Throw custom exceptions
        if (!this.accountService.allAccountsExist(accountBalances.keySet())) {
            throw new RuntimeException("Account Does not Exist");
        }
        if (accountBalances.values().stream().mapToDouble(Double::doubleValue).sum() != 0.0) {
            throw new RuntimeException("Does not balance");
        }
    }

    private Long getCurrentUserId() {
        return ((CustomUserDetails) this.userDetailsService.loadUserByUsername(
                UserContext.getCurrentUserName())).getUser().getId();
    }
}
