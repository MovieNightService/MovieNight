package com.kharkiv.movienight.service.movie;

import com.kharkiv.movienight.exception.movie.MovieNotFoundException;
import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.persistence.repository.MovieRepository;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.movie.MovieCreateDto;
import com.kharkiv.movienight.transport.dto.movie.MovieOutcomeDto;
import com.kharkiv.movienight.transport.dto.movie.MovieUpdateDto;
import com.kharkiv.movienight.transport.mapper.movie.MovieMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Setter(onMethod_ = @Autowired)
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private MovieMapper movieMapper;
    private Validator<Movie> validator;

    @Override
    public Long create(MovieCreateDto dto) {
        Movie movie = movieMapper.toEntity(dto);

        validator.validate(MethodType.CREATE, dto, movie);

        return movieRepository.save(movie).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public MovieOutcomeDto findByIdUnsafe(Long id) {
        return movieMapper.toDto(movieRepository.findById(id).orElseThrow(MovieNotFoundException::new));
    }

    @Override
    public void delete(Long id) {
        Movie movie = findById(id);

        validator.validate(MethodType.DELETE);

        movieRepository.delete(movie);
    }

    @Override
    public List<MovieOutcomeDto> findAll() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long update(Long id, MovieUpdateDto dto) {
        Movie movie = findById(id);

        validator.validate(MethodType.UPDATE, dto, movie);

        return movieMapper.toEntity(dto, movie).getId();
    }
}
