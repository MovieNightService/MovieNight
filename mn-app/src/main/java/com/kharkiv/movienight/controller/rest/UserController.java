package com.kharkiv.movienight.controller.rest;

import com.kharkiv.movienight.service.user.UserService;
import com.kharkiv.movienight.transport.dto.pageable.PageableDto;
import com.kharkiv.movienight.transport.dto.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @ApiOperation(value = "aboutMe", nickname = "aboutMe")
    public UserOutcomeDto user(Principal principal) {
        return userService.getUser(principal);
    }

    @PostMapping("registration")
    @ApiOperation(value = "registrationUser", nickname = "registrationUser")
    public Long registration(@RequestBody @Valid UserRegistrationDto dto) {
        return userService.registration(dto);
    }

    @PutMapping("update/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    @ApiOperation(value = "updateUser", nickname = "updateUser")
    public Long update(@PathVariable Long id, @Valid @RequestBody UserUpdateDto dto){
        return userService.update(id, dto);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    @ApiOperation(value = "deleteUser", nickname = "deleteUser")
    public Long delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @PutMapping("restore/{id}")
    @ApiOperation(value = "restoreUser", nickname = "restoreUser")
    public Long restore(@PathVariable Long id) {
        return userService.restore(id);
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation(value = "findAllUsers", nickname = "findAllUsers")
    public List<UserOutcomeDto> findAll(UserFindDto finder, PageableDto pageableDto) {
        return userService.findAll(finder, pageableDto);
    }

    @PutMapping("agreement")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation(value = "agreement", nickname = "agreement")
    public void agreement(@Valid @RequestBody UserAgreementDto dto) {
        userService.agreement(dto);
    }

    @PutMapping("change-role")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation(value = "changeRole", nickname = "changeRole")
    public void changeRole(@Valid @RequestBody UserRoleDto dto) {
        userService.changeRole(dto);
    }

    @PutMapping("update-email")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    @ApiOperation(value = "updateEmail", nickname = "updateEmail")
    public Long updateEmail(@Valid @RequestBody UserUpdateEmailDto dto) {
        return userService.update(dto);
    }

    @PutMapping("reset-password")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    @ApiOperation(value = "resetPassword", nickname = "resetPassword")
    public Long resetPassword(@Valid @RequestBody UserResetPasswordDto dto) {
        return userService.update(dto);
    }

    @PutMapping("upload-avatar")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    @ApiOperation(value = "uploadAvatar", nickname = "uploadAvatar")
    public Long uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
        return userService.uploadAvatar(avatar);
    }
}

