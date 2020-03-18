package com.appdomain.accesscontrol.accounting.contracts;

import java.util.List;

public class JournalEntryRequestDto {

    private JournalEntryDto journalEntryDto;
    private List<LedgerEntryDto> ledgerEntryDtos;

    public JournalEntryRequestDto() {
    }

    public JournalEntryRequestDto(final JournalEntryDto journalEntryDto, final List<LedgerEntryDto> ledgerEntryDtos) {
        this.journalEntryDto = journalEntryDto;
        this.ledgerEntryDtos = ledgerEntryDtos;
    }

    public JournalEntryDto getJournalEntryDto() {
        return journalEntryDto;
    }

    public List<LedgerEntryDto> getLedgerEntryDtos() {
        return ledgerEntryDtos;
    }
}
