package com.sparta.memotask.service;

import com.sparta.memotask.dto.CreateUserRequest;
import com.sparta.memotask.dto.LoginUserRequest;
import com.sparta.memotask.dto.UserResponse;
import com.sparta.memotask.entity.User;
import com.sparta.memotask.entity.UserRoleEnum;
import com.sparta.memotask.jwt.JwtUtil;
import com.sparta.memotask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public List<UserResponse> getUsers() {
        List<User> Users = userRepository.findAllByOrderByCreateAt();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : Users) {
            userResponseList.add(new UserResponse(user));
        }
        return userResponseList;
    }

    @Transactional
    public void createUser(CreateUserRequest createUserRequest) {
        Optional<User> checkUser = userRepository.findByUsername(createUserRequest.getUsername());
        if (checkUser.isPresent()) {
            throw new IllegalArgumentException("유저가 존재합니다");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (createUserRequest.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!createUserRequest.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(createUserRequest.getUsername(), createUserRequest.getPassword(), role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String loginUser(LoginUserRequest loginUserRequest) {
        String username = loginUserRequest.getUsername();
        String password = loginUserRequest.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if (!user.isValidPassword(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String generatedToken = jwtUtil.createToken(user.getUsername(), user.getRole());
        return generatedToken;

    }
}