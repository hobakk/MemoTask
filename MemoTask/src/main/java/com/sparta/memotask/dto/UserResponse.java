package com.sparta.memotask.dto;

import com.sparta.memotask.entity.User;
import com.sparta.memotask.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class UserResponse {
    private final String username;
    private final String kimminjae1;
    private UserRoleEnum role;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.kimminjae1 = user.getPassword();
        this.role = user.getRole();
    }
}
