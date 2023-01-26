package com.sparta.memotask.service;

import com.sparta.memotask.dto.*;
import com.sparta.memotask.entity.Comment;
import com.sparta.memotask.entity.Post;
import com.sparta.memotask.entity.User;
import com.sparta.memotask.jwt.JwtUtil;
import com.sparta.memotask.repository.CommentRepository;
import com.sparta.memotask.repository.PostRepository;
import com.sparta.memotask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public List<PostCommentResponse> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreateAt();
        List<PostCommentResponse> postResponsesList = new ArrayList<>();

        for (Post post : posts) {
            List<Comment> comments = commentRepository.findByPost_IdOrderByCreateAtDesc(post.getId());
            List<CommentResponse> commentResponseList = new ArrayList<>();
            for (Comment comment : comments) {
                commentResponseList.add(new CommentResponse(comment));
            }
            postResponsesList.add(new PostCommentResponse(post, commentResponseList));
        }
        return postResponsesList;
    }

    @Transactional
    public PostResponse createPost(CreatePostRequest createPostRequest, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("동일한 유저가 아님"));
        Post post = new Post(createPostRequest.getTitle(), user, createPostRequest.getContent());
        postRepository.save(post);
        return new PostResponse(post);
    }


    @Transactional
    public List<PostCommentResponse> getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("id 없음"));
        List<PostCommentResponse> postResponsesList = new ArrayList<>();

        List<Comment> comments = commentRepository.findByPost_IdOrderByCreateAtDesc(postId);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponseList.add(new CommentResponse(comment));
        }
        postResponsesList.add(new PostCommentResponse(post, commentResponseList));
        return postResponsesList;
    }

    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest, String username) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("id 없음"));

        System.out.println(post.getWriter().getUsername());
        if (!post.getWriter().getUsername().equals(username)) {
            throw new IllegalArgumentException("게시글의 작성자가 아닙니다");
        }

        post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());
        postRepository.save(post); // update 로 변경된 나머지를 다시 DB에 저장
        return new PostResponse(post);
    }

    @Transactional
    public void deletePost(Long postId, DeletePostRequest deletePostRequest, String username) {
        Post postDelete = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("id 없음"));  // Controller 에서 받은 id 값 번째 게시글을 postSaved 선언(?)

        if (!postDelete.getWriter().getUsername().equals(username)) {
            throw new IllegalArgumentException("게시글의 작성자가 아닙니다");
        }

        postRepository.delete(postDelete);
        System.out.println("success");
    }
}
