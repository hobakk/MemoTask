package com.sparta.memotask.dto;

import com.sparta.memotask.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostCommentResponse {
    private final String title;
    private final String writer;
    private final String content;
    private final LocalDateTime createAt;
    private final List<CommentResponse> contents;

    public PostCommentResponse(Post post, List<CommentResponse> contents) {
        this.title = post.getTitle();
        this.writer = post.getWriter().getUsername();
        this.content = post.getContent();
        this.createAt = post.getCreateAt();
        this.contents = contents;
    }
}

