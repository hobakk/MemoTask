package com.sparta.memotask.dto;

import lombok.Getter;

@Getter
public class LoginUserRequest {
    private String username;
    private String password;
}