package com.appdomain.accesscontrol.accounting.contracts;

public class JournalEntryReviewDto {

    private JournalEntryDto journalEntryDto;
    private boolean approved;
    private String reason;

    public JournalEntryDto getJournalEntryDto() {
        return journalEntryDto;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getReason() {
        return reason;
    }
}
