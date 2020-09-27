package com.kharkiv.movienight.controller;

import com.kharkiv.movienight.service.user.UserService;
import com.kharkiv.movienight.transport.dto.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "User Service", description = "REST API for User", tags = {"User"})
public class UserController {

    private final UserService userService;

    @GetMapping("/user/me")
    public UserOutcomeDto user(Principal principal) {
        return userService.getUser(principal);
    }

    @PostMapping("/user/registration")
    public Long registration(@Validated UserRegistrationDto dto){
        return userService.registration(dto);
    }

    @PutMapping("/user/update/{id}")
    public Long update(@PathVariable Long id, @Validated UserUpdateDto dto){
        return userService.update(id, dto);
    }

    @DeleteMapping("/user/delete/{id}")
    public Long delete(@PathVariable Long id){
        return userService.delete(id);
    }

    @PutMapping("/user/restore/{id}")
    public Long restore(@PathVariable Long id){
        return userService.restore(id);
    }

    @GetMapping("/user/find-all")
    public List<UserOutcomeDto> findAll(){
        return userService.findAll();
    }

    @PutMapping("/user/agreement")
    public void agreement(@Validated UserAgreementDto dto){
        userService.agreement(dto);
    }

    @PutMapping("/user/change-role")
    public void changeRole(@Validated UserRoleDto dto){
        userService.changeRole(dto);
    }

    @PutMapping("/user/update-email")
    public Long updateEmail( @RequestBody UserUpdateEmailDto dto){
        return userService.update(dto);
    }
}

