package com.kharkiv.movienight.controller;

import com.kharkiv.movienight.service.userevent.UserEventService;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user-events")
@RequiredArgsConstructor
@Api(value = "User-Event Service", description = "REST API for UserEvent", tags = {"UserEvent"})
public class UserEventController {

    private final UserEventService userEventService;

    @PostMapping
    @PreAuthorize(value = "hasAuthority('USER')")
    public Long create(@Valid @RequestBody UserEventCreateDto dto){
        return userEventService.create(dto);
    }
}
