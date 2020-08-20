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
@Table(name = "social_medias")
public class SocialMedia extends BaseEntity {

    public SocialMedia() {
    }

    public SocialMedia(UUID id, String link) {
        super(id);
        this.link = link;
    }

    @Column(nullable = false)
    private String link;

}
