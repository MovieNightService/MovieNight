package com.movienight.app.service.movie;

import com.movienight.app.service.utils.mapper.movie.MovieMapper;
import com.movienight.app.service.utils.validation.type.MethodType;
import com.movienight.app.service.utils.validation.validator.Validator;
import com.movienight.app.exception.movie.MovieNotFoundException;
import com.movienight.app.service.utils.pageable.PageableService;
import com.movienight.app.service.utils.specification.CustomSpecification;
import com.movienight.app.service.utils.specification.SearchCriteria;
import com.movienight.app.service.utils.specification.SearchOperation;
import com.movienight.foundation.dto.movie.*;
import com.movienight.foundation.dto.pageable.PageableDto;
import com.movienight.foundation.persistence.model.movie.Movie;
import com.movienight.foundation.persistence.model.user.User;
import com.movienight.foundation.persistence.repository.MovieRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
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
        User actor = getActorFromContext();

        validator.validate(MethodType.CREATE, dto, null);

        Movie movie = movieMapper.toEntity(dto);
        movie.setCreatedBy(actor);
        movie.setUpdatedBy(actor);

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

        validator.validate(MethodType.DELETE, movie);

        movieRepository.delete(movie);
    }

    @Override
    public List<MovieOutcomeDto> findAll(MovieFindDto finder, PageableDto pageableDto) {
        Pageable pageable = PageableService.getPageable(pageableDto);
        return movieRepository.findAll(createSpecification(finder), pageable).stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long update(Long id, MovieUpdateDto dto) {
        Movie movie = findById(id);

        validator.validate(MethodType.UPDATE, dto, movie);

        return movieMapper.toEntity(dto, movie).getId();
    }

    @Override
    public void uploadImage(Long id, MovieImageUploadDto dto) {
        Movie movie = findById(id);
        movieMapper.toEntity(dto, movie);
    }

    private Specification<Movie> createSpecification(MovieFindDto finder) {
        CustomSpecification<Movie> customSpecification = new CustomSpecification<>();

        List<SearchCriteria> criteriaList = Arrays.asList(
                new SearchCriteria("name", finder.getName(), SearchOperation.MATCH),
                new SearchCriteria("duration", finder.getDuration(), SearchOperation.EQUAL),
                new SearchCriteria("issue", finder.getIssue(), SearchOperation.EQUAL),
                new SearchCriteria("rating", finder.getRating(), SearchOperation.EQUAL),
                new SearchCriteria("language", finder.getLanguage(), SearchOperation.MATCH),
                new SearchCriteria("description", finder.getDescription(), SearchOperation.MATCH),
                new SearchCriteria("age", finder.getAge(), SearchOperation.EQUAL),
                new SearchCriteria("genre", finder.getGenre(), SearchOperation.MATCH)
        );

        criteriaList.stream()
                .filter(searchCriteria -> searchCriteria.getValue() != null)
                .forEach(customSpecification::addCriteria);

        return customSpecification;
    }
}
