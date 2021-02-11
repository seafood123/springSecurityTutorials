package com.excercise.jwtTutorial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class User {

    @JsonIgnore
    @Id
    @Column(name="USERID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name="NAME" ,length=50, unique = true)
    private String name;

    @JsonIgnore
    @Column(name="password", length=100)
    private String password;

    @Column(name="nickname", length=50)
    private String nickname;

    @JsonIgnore
    @Column(name="activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name="user_authority",
            joinColumns = {@JoinColumn(name="USERID", referencedColumnName = "USERID")},
            inverseJoinColumns = {@JoinColumn(name="AUTHORITY_NAME", referencedColumnName = "AUTHORITY_NAME")})
    private Set<Authority> authorities;
}
