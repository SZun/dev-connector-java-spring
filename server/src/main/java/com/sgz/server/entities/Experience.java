package com.sgz.server.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "experiences")
public class Experience extends Credential {

    public Experience() {
    }

    public Experience(UUID id, LocalDate fromDate, LocalDate toDate, boolean current, String description, String title, String company, String location) {
        super(id, fromDate, toDate, current, description);
        this.title = title;
        this.company = company;
        this.location = location;
    }

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String location;

}
