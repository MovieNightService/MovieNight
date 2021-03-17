package com.movienight.app.controller.rest;

import com.movienight.app.service.movie.MovieService;
import com.movienight.foundation.dto.movie.*;
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
@RequestMapping("movies")
@RequiredArgsConstructor
@Api(value = "Movie Service", description = "REST API for Movie", tags = {"Movie"})
@RefreshScope
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @ApiOperation(value = "createMovie", nickname = "createMovie")
    public Long create(@Valid @RequestBody MovieCreateDto dto) {
        return movieService.create(dto);
    }

    @GetMapping("{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @ApiOperation(value = "findByIdMovie", nickname = "findByIdMovie")
    public MovieOutcomeDto findById(@PathVariable Long id) {
        return movieService.findByIdUnsafe(id);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "deleteMovie", nickname = "deleteMovie")
    public void delete(@PathVariable Long id) {
        movieService.delete(id);
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'MANAGER')")
    @ApiOperation(value = "findAllMovies", nickname = "findAllMovies")
    public List<MovieOutcomeDto> findAll(MovieFindDto finder, PageableDto pageable) {
        return movieService.findAll(finder, pageable);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "updateMovie", nickname = "updateMovie")
    public Long update(@PathVariable Long id, @Valid @RequestBody MovieUpdateDto dto) {
        return movieService.update(id, dto);
    }

    @PutMapping("/upload-image/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "uploadImage", nickname = "uploadImage")
    public void uploadImage(@PathVariable Long id, @Valid @RequestBody MovieImageUploadDto dto) {
        movieService.uploadImage(id, dto);
    }
}
