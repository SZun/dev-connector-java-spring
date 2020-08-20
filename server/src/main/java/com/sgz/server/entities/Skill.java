package com.sgz.server.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "skills")
public class Skill extends BaseEntity {

    public Skill() {
    }

    public Skill(UUID id, String name) {
        super(id);
        this.name = name;
    }

    @Column(nullable = false)
    private String name;

}
