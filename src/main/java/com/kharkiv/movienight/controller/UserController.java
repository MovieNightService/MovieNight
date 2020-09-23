package com.kharkiv.movienight.controller;

import com.kharkiv.movienight.service.UserService;
import com.kharkiv.movienight.transport.dto.UserCreateDto;
import com.kharkiv.movienight.transport.dto.UserOutcomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/me")
    public UserOutcomeDto user(Principal principal) {
        return userService.getUser(principal);
    }

    @PostMapping("/user/registration")
    public Long create(UserCreateDto dto){
        return userService.create(dto);
    }
}

