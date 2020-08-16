package com.sgz.server.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "educations")
@AllArgsConstructor
@NoArgsConstructor
public class Education {

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String school;

    @Column(nullable = false)
    private String degree;

    @Column
    private String fieldOfStudy;

    @Column(nullable = false)
    protected LocalDate fromDate;

    @Column
    protected LocalDate toDate;

    @Column(nullable = false)
    protected boolean current;

    @Column
    protected String description;

}
