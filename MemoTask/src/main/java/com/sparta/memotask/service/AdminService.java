package com.sparta.memotask.service;

import com.sparta.memotask.dto.CommentResponse;
import com.sparta.memotask.dto.PostResponse;
import com.sparta.memotask.dto.UpdateCommentRequest;
import com.sparta.memotask.dto.UpdatePostRequest;
import com.sparta.memotask.entity.Comment;
import com.sparta.memotask.entity.Post;
import com.sparta.memotask.entity.User;
import com.sparta.memotask.repository.CommentRepository;
import com.sparta.memotask.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("id 없음"));
        post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());
        postRepository.save(post);
        return new PostResponse(post);
    }

    @Transactional
    public CommentResponse updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 없습니다."));
        comment.update(post, user, updateCommentRequest.getContent());
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post postDelete = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("id 없음"));
        postRepository.delete(postDelete);
        System.out.println("관리자 권한으로 삭제됌");
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글 없음"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        commentRepository.delete(comment);
        System.out.println("관리자 권한으로 삭제됌");
    }
}
