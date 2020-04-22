package com.appdomain.accesscontrol.accounting.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(schema = "PUBLIC", name = "Journal_Entry")
public class Journal_Entry {

    @Id
    @Column(name = "Id", unique = true, nullable = false, updatable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "Journal_Id", nullable = false)
    private Long journalId;

    @Column(name = "Creator_Id", nullable = false)
    private Long creatorId;

    @Column(name = "Create_Date", nullable = false, updatable = false)
    private Instant createDate;

    @Column(name = "Reviewer_Id")
    private Long reviewerId;

    @Column(name = "Review_Date", updatable = false)
    private Instant reviewDate;

    @Column(name = "Document_Id")
    private Long documentId;

    @Column(name = "Status")
    private String status;

    @Column(name = "Denied_Reason")
    private String deniedReason;

    public Journal_Entry() {
    }

    public Journal_Entry(final Long journalId,
                         final Long creatorId,
                         final Long reviewerId,
                         final Instant reviewDate,
                         final Long documentId,
                         final String status,
                         final String deniedReason) {
        this.journalId = journalId;
        this.creatorId = creatorId;
        this.createDate = Instant.now();
        this.reviewerId = reviewerId;
        this.reviewDate = reviewDate;
        this.documentId = documentId;
        this.status = status;
        this.deniedReason = deniedReason;
    }

    public Long getId() {
        return id;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Instant getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Instant reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeniedReason() {
        return deniedReason;
    }

    public void setDeniedReason(String deniedReason) {
        this.deniedReason = deniedReason;
    }
}
