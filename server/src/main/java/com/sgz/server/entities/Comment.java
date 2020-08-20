package com.sgz.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    public Comment() {
    }

    public Comment(UUID id, User user, String text, LocalDate commentDate) {
        super(id);
        this.user = user;
        this.text = text;
        this.commentDate = commentDate;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDate commentDate;

}
