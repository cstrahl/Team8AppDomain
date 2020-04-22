package com.appdomain.accesscontrol.accounting.repositories;

import com.appdomain.accesscontrol.accounting.domains.Ledger_Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerEntryRepository extends JpaRepository<Ledger_Entry, Long> {

    List<Ledger_Entry> findAllByAccountIdAndPendingFalse(final long accountId);

    List<Ledger_Entry> findAllByJournalEntryId(final long journalEntryId);
}
