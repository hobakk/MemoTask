package com.sparta.memotask.service;

import com.sparta.memotask.dto.CommentResponse;
import com.sparta.memotask.dto.CreateCommnetRequest;
import com.sparta.memotask.dto.UpdateCommentRequest;
import com.sparta.memotask.entity.Comment;
import com.sparta.memotask.entity.Post;
import com.sparta.memotask.entity.User;
import com.sparta.memotask.repository.CommentRepository;
import com.sparta.memotask.repository.PostRepository;
import com.sparta.memotask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse createComment(Long postId, CreateCommnetRequest createCommnetRequest, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당 유저 없음"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        Comment comment = new Comment(post, user, createCommnetRequest.getContent());
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    @Transactional
    public CommentResponse updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest, String username) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다.")
        );
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("id가 존재하지 않습니다.")
        );
        if (!comment.getWriter().getUsername().equals(username)) {
            throw new IllegalArgumentException("게시글의 작성자가 아닙니다.");
        }

        comment.update(post, user, updateCommentRequest.getContent());
        commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, String username) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글 없음"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당 유저 없음"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        if (!user.getUsername().equals(comment.getWriter().getUsername())) {
            throw new IllegalArgumentException("작성자가 다릅니다");
        } else {
            commentRepository.delete(comment);
            System.out.println("success");
        }
    }
}
