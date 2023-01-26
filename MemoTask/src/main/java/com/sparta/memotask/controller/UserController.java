package com.sparta.memotask.controller;

import com.sparta.memotask.dto.CreateUserRequest;
import com.sparta.memotask.dto.LoginUserRequest;
import com.sparta.memotask.dto.ResponseDto;
import com.sparta.memotask.dto.UserResponse;
import com.sparta.memotask.jwt.JwtUtil;
import com.sparta.memotask.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/api/findUser")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/api/signup")
    public ResponseDto createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        userService.createUser(createUserRequest);
        return new ResponseDto(200L, "회원가입 완료");
    }

    @PostMapping("/api/login")
    public ResponseDto loginUser(@RequestBody LoginUserRequest loginUserRequest, HttpServletResponse response) {
        String generatedToken = userService.loginUser(loginUserRequest);   // 요청에 대한 처리, 응답에 대한 처리는 여기서
        response.addHeader(jwtUtil.AUTHORIZATION_HEADER, generatedToken);
        return new ResponseDto(200L, "로그인 완료");
    }

}