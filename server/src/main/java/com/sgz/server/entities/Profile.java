package com.sgz.server.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "profiles")
public class Profile extends BaseEntity {

    public Profile() {
    }

    public Profile(UUID id, User user, String handle, String company, String website, String location, String description, String githubUsername, Set<Skill> skills, Set<Education> educations, Set<Experience> experiences, Set<SocialMedia> socialMedias) {
        super(id);
        this.user = user;
        this.handle = handle;
        this.company = company;
        this.website = website;
        this.location = location;
        this.description = description;
        this.githubUsername = githubUsername;
        this.skills = skills;
        this.educations = educations;
        this.experiences = experiences;
        this.socialMedias = socialMedias;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String handle;

    @Column
    private String company;

    @Column
    private String website;

    @Column
    private String location;

    @Column
    private String description;

    @Column(nullable = false)
    private String githubUsername;

    @ManyToMany
    @JoinTable(name = "profiles_skills",
            joinColumns = {@JoinColumn(name = "profile_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")})
    private Set<Skill> skills= new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profiles_educations",
            joinColumns = {@JoinColumn(name = "profile_id")},
            inverseJoinColumns = {@JoinColumn(name = "education_id")})
    private Set<Education> educations = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profiles_experiences",
            joinColumns = {@JoinColumn(name = "profile_id")},
            inverseJoinColumns = {@JoinColumn(name = "experiences_id")})
    private Set<Experience> experiences = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profiles_social_medias",
            joinColumns = {@JoinColumn(name = "profile_id")},
            inverseJoinColumns = {@JoinColumn(name = "social_media_id")})
    private Set<SocialMedia> socialMedias = new HashSet<>();

}
