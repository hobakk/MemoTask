package com.sparta.memotask.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto {
    private String subject;
    private String username;
    private String contents;
    private String password;
}
