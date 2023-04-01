package com.diegodev.hulkstore.data.entity.user;

import com.diegodev.hulkstore.data.constants.Roles;
import com.diegodev.hulkstore.data.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "application_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "name")
    private String name;
    @JsonIgnore
    private String hashedPassword;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Roles> roles;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

}
