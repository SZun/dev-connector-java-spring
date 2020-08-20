package com.sgz.server.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "educations")
public class Education extends Credential {

    public Education() {
    }

    public Education(UUID id, LocalDate fromDate, LocalDate toDate, boolean current, String description, String school, String degree, String fieldOfStudy) {
        super(id, fromDate, toDate, current, description);
        this.school = school;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
    }

    @Column(nullable = false)
    private String school;

    @Column(nullable = false)
    private String degree;

    @Column
    private String fieldOfStudy;

}
