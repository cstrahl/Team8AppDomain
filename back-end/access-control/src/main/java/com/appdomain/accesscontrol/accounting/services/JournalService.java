package com.appdomain.accesscontrol.accounting.services;

import com.appdomain.accesscontrol.accounting.contracts.JournalDto;
import com.appdomain.accesscontrol.accounting.domains.Journal;
import com.appdomain.accesscontrol.accounting.repositories.JournalRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JournalService {

    private final JournalRepository journalRepository;

    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public List<JournalDto> getAllJournals() {
        final List<Journal> journals = this.journalRepository.findAllByOrderByStartDateDesc();
        if (journals.isEmpty() || journals.get(0).getEndDate().isBefore(Instant.now())) {
            journals.add(0, this.journalRepository.save(new Journal()));
        }
        return journals.stream().map(JournalDto::new).collect(Collectors.toList());
    }

    public JournalDto getCurrentJournal() {
        Optional<Journal> newestJournal = this.journalRepository.findFirstByOrderByStartDateDesc();
        if (newestJournal.isEmpty() || newestJournal.get().getEndDate().isBefore(Instant.now())) {
            return new JournalDto(this.journalRepository.save(new Journal()));
        }
        return new JournalDto(newestJournal.get());
    }

    public JournalDto getJournalById(long id) {
        return new JournalDto(this.journalRepository.getOne(id));
    }
}
