package com.movienight.app.controller.rest;

import com.movienight.app.service.event.EventService;
import com.movienight.foundation.dto.event.EventCreateDto;
import com.movienight.foundation.dto.event.EventFindDto;
import com.movienight.foundation.dto.event.EventOutcomeDto;
import com.movienight.foundation.dto.event.EventUpdateDto;
import com.movienight.foundation.dto.pageable.PageableDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
@Api(value = "Event Service", description = "REST API for Event", tags = {"Event"})
@RefreshScope
public class EventController {

    private final EventService eventService;

    @PostMapping
    @PreAuthorize(value = "hasAuthority('MANAGER')")
    @ApiOperation(value = "createEvent", nickname = "createEvent")
    public Long create(@Valid @RequestBody EventCreateDto dto){
        return eventService.create(dto);
    }

    @GetMapping("{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MANAGER','USER')")
    @ApiOperation(value = "findByIdEvent", nickname = "findByIdEvent")
    public EventOutcomeDto findById(@PathVariable Long id){
        return eventService.findById(id);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @ApiOperation(value = "deleteEvent", nickname = "deleteEvent")
    public Long delete(@PathVariable Long id) {
        return eventService.delete(id);
    }

    @PutMapping("restore/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @ApiOperation(value = "restoreEvent", nickname = "restoreEvent")
    public Long restore(@PathVariable Long id) {
        return eventService.restore(id);
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('ADMIN','MANAGER','USER')")
    @ApiOperation(value = "findAllEvents", nickname = "findAllEvents")
    public List<EventOutcomeDto> findAll(EventFindDto finder, PageableDto pageable) {
        return eventService.findAll(finder, pageable);
    }

    @PutMapping("update/{id}")
    @PreAuthorize(value = "hasAuthority('MANAGER')")
    @ApiOperation(value = "updateEvent", nickname = "updateEvent")
    public Long update(@PathVariable Long id, @Valid @RequestBody EventUpdateDto dto){
        return eventService.update(id, dto);
    }
}
