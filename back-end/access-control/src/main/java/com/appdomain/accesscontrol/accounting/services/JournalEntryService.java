package com.appdomain.accesscontrol.accounting.services;

import com.appdomain.accesscontrol.accounting.contracts.JournalEntryDto;
import com.appdomain.accesscontrol.accounting.contracts.JournalEntryRequestDto;
import com.appdomain.accesscontrol.accounting.contracts.JournalEntryReviewDto;
import com.appdomain.accesscontrol.accounting.contracts.LedgerEntryDto;
import com.appdomain.accesscontrol.accounting.domains.JournalEntry;
import com.appdomain.accesscontrol.accounting.repositories.JournalEntryRepository;
import com.appdomain.accesscontrol.accounting.utils.JournalEntryStatus;
import com.appdomain.accesscontrol.security.utils.UserContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.appdomain.accesscontrol.accounting.utils.JournalEntryStatus.*;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;

    private final JournalService journalService;
    private final LedgerEntryService ledgerEntryService;
    private final AccountService accountService;

    public JournalEntryService(final JournalEntryRepository journalEntryRepository,
                               final JournalService journalService,
                               final LedgerEntryService ledgerEntryService,
                               final AccountService accountService) {
        this.journalEntryRepository = journalEntryRepository;
        this.journalService = journalService;
        this.ledgerEntryService = ledgerEntryService;
        this.accountService = accountService;
    }

    public List<JournalEntryDto> getByFilters(final long journalId,
                                              final Set<JournalEntryStatus> filters) {
        final Long userId = UserContext.getCurrentUser().getUser().getId();
        return this.journalEntryRepository.findAllByJournalIdAndStatusIn(journalId,
                filters.stream().map(Enum::name).collect(Collectors.toSet()))
                .stream()
                .filter(journalEntry -> journalEntry.getCreatorId().equals(userId)
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
        final JournalEntry journalEntry = this.getJournalEntry(journalEntryRequestDto.getJournalEntryDto());
        final List<LedgerEntryDto> ledgerEntryDtos = journalEntryRequestDto.getLedgerEntryDtos();
        this.verifyAccountBalances(ledgerEntryDtos);

        this.journalEntryRepository.save(journalEntry);
        return new JournalEntryRequestDto(new JournalEntryDto(journalEntry),
                this.ledgerEntryService.createAndSaveAll(ledgerEntryDtos, journalEntry.getId()).stream()
                        .map(LedgerEntryDto::new)
                        .collect(Collectors.toList()));
    }

    public JournalEntryDto reviewRequest(final JournalEntryReviewDto reviewDto) {
        final JournalEntry journalEntry = this.journalEntryRepository.findById(reviewDto.getJournalEntryDto().getId())
                .orElseThrow(() -> new RuntimeException("Could not find request"));
        this.verifyStatus(journalEntry);

        if (reviewDto.isApproved()) {
            journalEntry.setStatus(APPROVED.name());
            this.ledgerEntryService.acceptAllForJournalEntry(journalEntry.getId());
        } else {
            journalEntry.setStatus(DENIED.name());
            journalEntry.setDeniedReason(reviewDto.getReason());
        }
        journalEntry.setReviewerId(UserContext.getCurrentUser().getUser().getId());
        journalEntry.setReviewDate(Instant.now());
        return new JournalEntryDto(this.journalEntryRepository.save(journalEntry));
    }

    private JournalEntry getJournalEntry(final JournalEntryDto journalEntryDto) {
        final Long userId = UserContext.getCurrentUser().getUser().getId();
        if (journalEntryDto.getId() != null) {
            final Optional<JournalEntry> optional = this.journalEntryRepository.findById(
                    journalEntryDto.getId());
            if (optional.isPresent()) {
                final JournalEntry journalEntry = optional.get();
                if (!journalEntry.getCreatorId().equals(userId)) {
                    throw new RuntimeException("Cannot edit someone else's request");
                }
                this.verifyStatus(journalEntry);
                journalEntry.setStatus(journalEntryDto.getStatus().name());
                return this.journalEntryRepository.save(journalEntry);
            }
        }
        return this.journalEntryRepository.save(new JournalEntry(
                this.journalService.getCurrentJournal().getId(), userId,
                null, null, null, CREATED.name(), null));
    }

    private void verifyStatus(JournalEntry journalEntry) {
        if (!journalEntry.getStatus().equals(CREATED.name())
                || !journalEntry.getStatus().equals(PENDING.name())) {
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
                break;
            } else if (ledgerEntryDto.getDebit() != 0.0) {
                accountBalances.put(accountId,
                        accountBalances.getOrDefault(accountId, 0.0) + ledgerEntryDto.getDebit());
                break;
            }
            throw new RuntimeException("No Value");
        }
        //TODO: Throw custom exceptions
        if (!this.accountService.allAccountsExist(accountBalances.keySet())) {
            throw new RuntimeException("Account Does not Exist");
        }
        if (accountBalances.values().stream().anyMatch(balance -> !balance.equals(0.0))) {
            throw new RuntimeException("Does not balance");
        }
    }
}
