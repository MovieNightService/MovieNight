package com.kharkiv.movienight.controller;

import com.kharkiv.movienight.service.userevent.UserEventService;
import com.kharkiv.movienight.transport.dto.pageable.PageableDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventCreateDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventFindDto;
import com.kharkiv.movienight.transport.dto.userevent.UserEventOutcomeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user-events")
@RequiredArgsConstructor
@Api(value = "User-Event Service", description = "REST API for UserEvent", tags = {"UserEvent"})
public class UserEventController {

    private final UserEventService userEventService;

    @PostMapping
    @PreAuthorize(value = "hasAuthority('USER')")
    @ApiOperation(value = "createUserEvent", nickname = "createUserEvent")
    public Long create(@Valid @RequestBody UserEventCreateDto dto){
        return userEventService.create(dto);
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER', 'USER')")
    @ApiOperation(value = "findAllUserEvents", nickname = "findAllUserEvents")
    public List<UserEventOutcomeDto> findAll(UserEventFindDto finder, PageableDto pageableDto){
        return userEventService.findAll(finder, pageableDto);
    }

    @DeleteMapping
    @PreAuthorize(value = "hasAnyAuthority('MANAGER', 'USER')")
    @ApiOperation(value = "deleteUserEvent", nickname = "deleteUserEvent")
    public Long delete(Long id){
        return userEventService.delete(id);
    }

}
