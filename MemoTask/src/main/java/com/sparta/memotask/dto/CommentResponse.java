package com.sparta.memotask.dto;


import com.sparta.memotask.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long commentId;
    private final String writer;
    private final String content;
    private final LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.writer = comment.getWriter().getUsername();
        this.content = comment.getContent();
        this.createdAt = comment.getCreateAt();
    }
}
