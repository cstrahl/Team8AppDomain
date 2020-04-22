package com.appdomain.accesscontrol.accounting.contracts;

import com.appdomain.accesscontrol.accounting.domains.Journal_Entry;
import com.appdomain.accesscontrol.accounting.utils.JournalEntryStatus;

import java.time.Instant;
import java.util.Optional;

public class JournalEntryDto {

    private Long id;
    private long journalId;
    private long creatorId;
    private Instant createDate;
    private long reviewerId;
    private Instant reviewDate;
    private JournalEntryStatus status;
    private String deniedReason;

    public JournalEntryDto() {
    }

    public JournalEntryDto(final Journal_Entry journalEntry) {
        this.id = journalEntry.getId();
        this.journalId = journalEntry.getJournalId();
        this.creatorId = journalEntry.getCreatorId();
        this.createDate = journalEntry.getCreateDate();
        if (Optional.ofNullable(journalEntry.getReviewerId()).isPresent()) {
            this.reviewerId = journalEntry.getReviewerId();
            this.reviewDate = journalEntry.getReviewDate();
            if (Optional.ofNullable(journalEntry.getDeniedReason()).isPresent()) {
                this.deniedReason = journalEntry.getDeniedReason();
            }
        }
        this.status = JournalEntryStatus.valueOf(journalEntry.getStatus());
    }

    public Long getId() {
        return id;
    }

    public long getJournalId() {
        return journalId;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public long getReviewerId() {
        return reviewerId;
    }

    public Instant getReviewDate() {
        return reviewDate;
    }

    public JournalEntryStatus getStatus() {
        return status;
    }

    public String getDeniedReason() {
        return deniedReason;
    }
}
