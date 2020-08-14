package com.sgz.server.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
public class Credibility {

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column
    private LocalDate toDate;

    @Column(nullable = false)
    private boolean current;

    @Column
    private String description;

}
