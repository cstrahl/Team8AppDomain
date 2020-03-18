package com.appdomain.accesscontrol.accounting.controllers;

import com.appdomain.accesscontrol.accounting.contracts.JournalDto;
import com.appdomain.accesscontrol.accounting.services.JournalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/journals")
public class JournalController {

    private final JournalService journalService;

    public JournalController(final JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<JournalDto> getAllJournals() {
        return this.journalService.getAllJournals();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public JournalDto getJournal(final @PathVariable long id) {
        return this.journalService.getJournalById(id);
    }

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public JournalDto getCurrentJournal() {
        return this.journalService.getCurrentJournal();
    }
}
