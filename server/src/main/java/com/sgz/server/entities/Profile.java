package com.sgz.server.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "profiles")
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

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
