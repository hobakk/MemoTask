package com.sparta.memotask.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;


@Getter
@Entity
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn
    private List<Comment> comments;

    private String title;

    @ManyToOne
    @JoinColumn
    private User writer;

    private String content;

    public Post() {}

    public Post(String title, User writer, String content) {

        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public void update(String title, String content) {

        this.title = title;
        this.content = content;
    }
}
