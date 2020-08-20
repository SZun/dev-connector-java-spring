package com.sgz.server.entities;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotBlank(message = "Password can not be blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must follow rules")
    @Column(nullable = false)
    @NonNull
    private String password;

    @NotBlank(message = "Username can not be blank")
    @Size(max = 50, message = "Username can not be more than 50 characters")
    @Column(unique = true, nullable = false)
    @NonNull
    private String username;


    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    public User(){
        super();
    }

    public User(UUID id, String password, String username) {
        super(id);
        this.password = password;
        this.username = username;
    }

    public User(String password, String username, Set<Role> roles) {
        super();
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

    public User(UUID id, String password, String username, Set<Role> roles) {
        super(id);
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

}