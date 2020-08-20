package com.sgz.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    public Post() {
    }

    public Post(UUID id, User user, String text, String title, LocalDate postDate, List<Comment> comments, List<User> likes) {
        super(id);
        this.user = user;
        this.text = text;
        this.title = title;
        this.postDate = postDate;
        this.comments = comments;
        this.likes = likes;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate postDate;

    @ManyToMany
    @JoinTable(name = "posts_comments",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "comment_id")})
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "posts_likes",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> likes = new ArrayList<>();

}
