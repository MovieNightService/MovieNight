package com.kharkiv.movienight.controller;

import com.kharkiv.movienight.service.event.EventService;
import com.kharkiv.movienight.transport.dto.event.EventCreateDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
@Api(value = "Event Service", description = "REST API for Event", tags = {"Event"})
public class EventController {

    private final EventService eventService;

    @PostMapping
    @PreAuthorize(value = "hasAuthority('MANAGER')")
    public Long create(@Valid @RequestBody EventCreateDto dto){
        return eventService.create(dto);
    }
}
