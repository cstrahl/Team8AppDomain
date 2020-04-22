package com.appdomain.accesscontrol.accounting.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(schema = "PUBLIC", name = "Journals")
public class Journal {

    @Id
    @Column(name = "Id", unique = true, nullable = false, updatable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "Start_Date", nullable = false, updatable = false)
    private Instant startDate;

    @Column(name = "End_Date", nullable = false, updatable = false)
    private Instant endDate;

    public Journal() {
        this.startDate = Instant.now().truncatedTo(ChronoUnit.DAYS);
        this.endDate = this.startDate
                .plus(1, ChronoUnit.DAYS)
                .minus(1, ChronoUnit.NANOS);
    }

    public Long getId() {
        return id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }
}
