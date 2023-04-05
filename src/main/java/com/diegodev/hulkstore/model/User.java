package com.diegodev.hulkstore.model;


import com.diegodev.hulkstore.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "hashed_password")
    @JsonIgnore
    private String hashedPassword;

    @JsonIgnore
    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY)
    private List<Order> orders;

    public User(String name, String username, String email, Set<Role> roles, String hashedPassword) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.hashedPassword = hashedPassword;
    }
}
