package com.sparta.memotask.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommnetRequest {
    private String content;

    public CreateCommnetRequest(String content) {
        this.content = content;
    }
}
