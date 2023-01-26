package com.sparta.memotask.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // Getter 와 NoArgsConstructor 를 사용하면 알아서 데이터까 들어감
public class CreatePostRequest {
    private String title;
    private String username;
    private String password;
    private String content;

    public CreatePostRequest(String title, String username, String password, String content) {
        this.title = title;
        this.username = username;
        this.password = password;
        this.content = content;
    }
}
