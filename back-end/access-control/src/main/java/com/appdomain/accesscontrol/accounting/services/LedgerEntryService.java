package com.appdomain.accesscontrol.accounting.services;

import com.appdomain.accesscontrol.accounting.contracts.LedgerEntryDto;
import com.appdomain.accesscontrol.accounting.domains.Account;
import com.appdomain.accesscontrol.accounting.domains.Ledger_Entry;
import com.appdomain.accesscontrol.accounting.repositories.LedgerEntryRepository;
import com.appdomain.accesscontrol.accounting.utils.TransactionType;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LedgerEntryService {

    private final LedgerEntryRepository ledgerEntryRepository;

    private final AccountService accountService;

    public LedgerEntryService(final LedgerEntryRepository ledgerEntryRepository, final AccountService accountService) {
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.accountService = accountService;
    }

    public List<LedgerEntryDto> getAllEntriesInAccount(final long accountId) {
        return this.ledgerEntryRepository.findAllByAccountIdAndPendingFalse(accountId)
                .stream()
                .map(LedgerEntryDto::new)
                .collect(Collectors.toList());
    }

    public List<LedgerEntryDto> getAllEntriesInJournalEntry(final long journalEntryId) {
        return this.ledgerEntryRepository.findAllByJournalEntryId(journalEntryId)
                .stream()
                .map(LedgerEntryDto::new)
                .collect(Collectors.toList());
    }

    public LedgerEntryDto getEntry(long id) {
        return new LedgerEntryDto(this.ledgerEntryRepository.getOne(id));
    }

    public List<Ledger_Entry> createAndSaveAll(final List<LedgerEntryDto> ledgerEntryDtos, final long journalEntryId) {
        this.ledgerEntryRepository.deleteAll(this.ledgerEntryRepository.findAllByJournalEntryId(journalEntryId));
        return this.ledgerEntryRepository.saveAll(
                ledgerEntryDtos.stream().map(ledgerEntryDto ->
                        new Ledger_Entry(ledgerEntryDto.getAccountId(), journalEntryId, ledgerEntryDto.getCredit(),
                                ledgerEntryDto.getDebit(), ledgerEntryDto.getDescription()))
                        .collect(Collectors.toList()));
    }

    public void acceptAllForJournalEntry(final Long id) {
        final Map<Long, Account> accountMap = new HashMap<>();
        final List<Ledger_Entry> entries = this.ledgerEntryRepository.findAllByJournalEntryId(id);
        entries.forEach(ledgerEntry -> {
            final Long accountId = ledgerEntry.getAccountId();
            if (!accountMap.containsKey(accountId)) {
                accountMap.put(accountId, this.accountService.getAccountById(accountId));
            }
            final Account account = accountMap.get(accountId);
            account.setCredit(account.getCredit() + ledgerEntry.getCredit());
            account.setDebit(account.getDebit() + ledgerEntry.getDebit());
            ledgerEntry.setPending(false);
        });
        final Collection<Account> accounts = accountMap.values();
        accounts.forEach(account -> {
            if (TransactionType.DEBIT.name().equals(account.getSide())) {
                account.setBalance(account.getDebit() - account.getCredit());
            }
            else account.setBalance(account.getCredit() - account.getDebit());
        });
        this.accountService.saveAll(accounts);
        this.ledgerEntryRepository.saveAll(entries);
    }
}
