package com.appdomain.accesscontrol.accounting.controllers;

import com.appdomain.accesscontrol.accounting.contracts.JournalEntryDto;
import com.appdomain.accesscontrol.accounting.contracts.JournalEntryRequestDto;
import com.appdomain.accesscontrol.accounting.contracts.JournalEntryReviewDto;
import com.appdomain.accesscontrol.accounting.services.JournalEntryService;
import com.appdomain.accesscontrol.accounting.utils.JournalEntryStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/journal-entries")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    public JournalEntryController(final JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping("/requests")
    @PreAuthorize("isAuthenticated()")
    public List<JournalEntryDto> getAllRequests(@RequestParam final long journalId,
                                                @RequestParam final Set<JournalEntryStatus> filters) {
        return this.journalEntryService.getByFilters(journalId, filters);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public JournalEntryDto getEntryById(@PathVariable final long id) {
        return this.journalEntryService.getById(id);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<JournalEntryDto> getAllEntries(@RequestParam final long journalId) {
        return this.journalEntryService.getAllEntries(journalId);
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public JournalEntryRequestDto createRequest(@RequestBody final JournalEntryRequestDto journalEntryRequestDto) {
        return this.journalEntryService.createRequest(journalEntryRequestDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public JournalEntryDto reviewRequest(@RequestBody final JournalEntryReviewDto reviewDto) {
        return this.journalEntryService.reviewRequest(reviewDto);
    }
}
