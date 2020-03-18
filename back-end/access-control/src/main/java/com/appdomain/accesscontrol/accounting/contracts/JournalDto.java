package com.appdomain.accesscontrol.accounting.contracts;

import com.appdomain.accesscontrol.accounting.domains.Journal;

import java.time.Instant;

public class JournalDto {

    private long id;
    private Instant startDate;
    private Instant endDate;

    public JournalDto() {
    }

    public JournalDto(final Journal journal) {
        this.id = journal.getId();
        this.startDate = journal.getStartDate();
        this.endDate = journal.getEndDate();
    }

    public long getId() {
        return id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }
}
