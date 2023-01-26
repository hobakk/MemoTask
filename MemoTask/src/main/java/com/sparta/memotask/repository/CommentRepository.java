package com.sparta.memotask.repository;

import com.sparta.memotask.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //    List<Comment> findAllByOrderByCreateAt();
//    Optional<Comment> findByUsername(String username);
    List<Comment> findByPost_IdOrderByCreateAtDesc(Long postId);
}
