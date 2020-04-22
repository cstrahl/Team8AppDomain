package com.appdomain.accesscontrol.accounting.repositories;

import com.appdomain.accesscontrol.accounting.domains.Journal_Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JournalEntryRepository extends JpaRepository<Journal_Entry, Long> {

    List<Journal_Entry> findAllByJournalId(final long journalId);

    List<Journal_Entry> findAllByJournalIdAndStatusIn(final long journalId, final Set<String> status);
}
