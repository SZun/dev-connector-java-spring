package com.sgz.server.entities;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.UUID;

@Data
public abstract class Credential extends BaseEntity {

    public Credential() {
    }

    public Credential(UUID id, LocalDate fromDate, LocalDate toDate, boolean current, String description) {
        super(id);
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.current = current;
        this.description = description;
    }

    @Column(nullable = false)
    protected LocalDate fromDate;

    @Column
    protected LocalDate toDate;

    @Column(nullable = false)
    protected boolean current;

    @Column
    protected String description;

}
