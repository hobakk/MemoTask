package com.sparta.memotask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "commnet_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }
}