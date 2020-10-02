package com.kharkiv.movienight.controller;

import com.kharkiv.movienight.service.user.UserService;
import com.kharkiv.movienight.transport.dto.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Api(value = "User Service", description = "REST API for User", tags = {"User"})
public class UserController {

    private final UserService userService;

    @GetMapping("me")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    public UserOutcomeDto user(Principal principal) {
        return userService.getUser(principal);
    }

    @PostMapping("registration")
    public Long registration(@RequestBody @Valid UserRegistrationDto dto){
        return userService.registration(dto);
    }

    @PutMapping("update/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    public Long update(@PathVariable Long id, @Valid @RequestBody UserUpdateDto dto){
        return userService.update(id, dto);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    public Long delete(@PathVariable Long id){
        return userService.delete(id);
    }

    @PutMapping("restore/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    public Long restore(@PathVariable Long id){
        return userService.restore(id);
    }

    @GetMapping("find-all")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public List<UserOutcomeDto> findAll(){
        return userService.findAll();
    }

    @PutMapping("agreement")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    public void agreement(@Valid @RequestBody UserAgreementDto dto){
        userService.agreement(dto);
    }

    @PutMapping("change-role")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public void changeRole(@Valid @RequestBody UserRoleDto dto){
        userService.changeRole(dto);
    }

    @PutMapping("update-email")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    public Long updateEmail(@Valid @RequestBody UserUpdateEmailDto dto){
        return userService.update(dto);
    }

    @PutMapping("reset-password")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    public Long resetPassword(@Valid @RequestBody UserResetPasswordDto dto){
        return userService.update(dto);
    }

}

