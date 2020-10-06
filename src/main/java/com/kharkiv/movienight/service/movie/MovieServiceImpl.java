package com.kharkiv.movienight.service.movie;

import com.kharkiv.movienight.exception.movie.MovieNotFoundException;
import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.repository.MovieRepository;
import com.kharkiv.movienight.transport.dto.movie.MovieCreateDto;
import com.kharkiv.movienight.transport.mapper.movie.MovieMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@Setter(onMethod_ = @Autowired)
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private MovieMapper movieMapper;

    @Override
    @Transactional(readOnly = true)
    public Movie findByIdAndDeletedFalse(Long id) {
        return movieRepository.findByIdAndDeletedFalse(id).orElseThrow(MovieNotFoundException::new);
    }

    @Override
    public Long create(MovieCreateDto dto) {
        Movie movie = movieMapper.toEntity(dto);
        return movieRepository.save(movie).getId();
    }
}
