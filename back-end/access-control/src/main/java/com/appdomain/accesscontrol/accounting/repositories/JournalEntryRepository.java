package com.appdomain.accesscontrol.accounting.repositories;

import com.appdomain.accesscontrol.accounting.domains.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry> findAllByJournalId(final long journalId);

    List<JournalEntry> findAllByJournalIdAndStatusIn(final long journalId, final Set<String> status);
}
