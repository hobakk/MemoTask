package com.sparta.memotask.controller;

import com.sparta.memotask.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/api/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto) {
        return memoService.createPost(requestDto);
    }

    @GetMapping("/api/posts")
    @ResponseBody
    public List<Post> getPosts() {
        return memoService.getPosts();
    }

    @GetMapping("/api/posts/{id}")
    @ResponseBody
    public Post getPost(@PathVariable Long id) {
        return memoService.getPost(id);
    }

    @PutMapping("/api/posts/{id}")
    @ResponseBody
    public Post updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDto requestDto) {
        return memoService.update(id, requestDto);
    }

    @DeleteMapping("/api/posts/{id}")
    public Boolean deletePost(@PathVariable Long id, @RequestBody PasswordRequestDto requestDto) {
        return memoService.deletePost(id, requestDto);
    }
}
