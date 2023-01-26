package com.sparta.memotask.controller;

import com.sparta.memotask.dto.CommentResponse;
import com.sparta.memotask.dto.PostResponse;
import com.sparta.memotask.dto.UpdateCommentRequest;
import com.sparta.memotask.dto.UpdatePostRequest;
import com.sparta.memotask.entity.User;
import com.sparta.memotask.entity.UserRoleEnum;
import com.sparta.memotask.jwt.JwtUtil;
import com.sparta.memotask.repository.UserRepository;
import com.sparta.memotask.service.AdminService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    @PutMapping("/api/admin/{postId}")
    public PostResponse updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequest updatePostRequest, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
                String username = claims.getSubject();
                User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("없는 유저"));
                if (!user.getRole().equals(UserRoleEnum.ADMIN)) { throw new IllegalArgumentException("관리자가 아닙니다"); }
                return adminService.updatePost(postId, updatePostRequest);
            } else { throw  new IllegalArgumentException("유효하지 않은 토큰"); }
        } else { throw new IllegalArgumentException("토큰값이 잘못됌"); }
    }

    @PutMapping("/api/admin/{postId}/comment/{commentId}")
    public CommentResponse updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody UpdateCommentRequest updateCommentRequest, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
                String username = claims.getSubject();
                User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("없는 유저"));
                if (!user.getRole().equals(UserRoleEnum.ADMIN)) { throw new IllegalArgumentException("관리자가 아닙니다"); }
                return adminService.updateComment(postId, commentId, updateCommentRequest, user);
            } else { throw  new IllegalArgumentException("유효하지 않은 토큰"); }
        } else { throw new IllegalArgumentException("토큰값이 잘못됌"); }
    }

    @DeleteMapping("/api/admin/{postId}")
    public void deletePost(@PathVariable Long postId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
                String username = claims.getSubject();
                User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("없는 유저"));
                System.out.println(user.getRole());
                if (!user.getRole().equals(UserRoleEnum.ADMIN)) { throw new IllegalArgumentException("관리자가 아닙니다"); }
                adminService.deletePost(postId);
            } else { throw  new IllegalArgumentException("유효하지 않은 토큰"); }
        } else { throw new IllegalArgumentException("토큰값이 잘못됨"); }
    }

    @DeleteMapping("/api/admin/{postId}/comment/{commentId}")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
                String username = claims.getSubject();
                User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("없는 유저"));
                if (!user.getRole().equals(UserRoleEnum.ADMIN)) { throw new IllegalArgumentException("관리자가 아닙니다"); }
                adminService.deleteComment(postId, commentId);
            } else { throw  new IllegalArgumentException("유효하지 않은 토큰"); }
        } else { throw new IllegalArgumentException("토큰값이 잘못됨"); }
    }

}