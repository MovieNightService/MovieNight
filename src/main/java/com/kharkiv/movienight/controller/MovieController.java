package com.kharkiv.movienight.controller;

import com.kharkiv.movienight.service.movie.MovieService;
import com.kharkiv.movienight.transport.dto.movie.MovieCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
@Api(value = "Movie Service", description = "REST API for Movie", tags = {"Movie"})
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @ApiOperation(value = "createMovie", nickname = "createMovie")
    public Long create(@Valid @RequestBody MovieCreateDto dto){
        return movieService.create(dto);
    }
}
