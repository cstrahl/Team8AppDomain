package com.appdomain.accesscontrol.accounting.controllers;

import com.appdomain.accesscontrol.accounting.contracts.LedgerEntryDto;
import com.appdomain.accesscontrol.accounting.services.LedgerEntryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ledger-entries")
public class LedgerEntryController {

    private final LedgerEntryService ledgerEntryService;

    public LedgerEntryController(final LedgerEntryService ledgerEntryService) {
        this.ledgerEntryService = ledgerEntryService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<LedgerEntryDto> getAllEntries(@RequestParam final long accountId) {
        return this.ledgerEntryService.getAllEntriesInAccount(accountId);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<LedgerEntryDto> getAllEntriesInJournalEntry(@RequestParam final long journalEntryId) {
        return this.ledgerEntryService.getAllEntriesInJournalEntry(journalEntryId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public LedgerEntryDto getEntry(@PathVariable final long id){
        return this.ledgerEntryService.getEntry(id);
    }
}
