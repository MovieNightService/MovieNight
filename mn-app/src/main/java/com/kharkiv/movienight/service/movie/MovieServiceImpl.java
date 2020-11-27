package com.kharkiv.movienight.service.movie;

import com.kharkiv.movienight.exception.movie.MovieNotFoundException;
import com.kharkiv.movienight.persistence.model.event.Event;
import com.kharkiv.movienight.persistence.model.movie.Genre;
import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.repository.MovieRepository;
import com.kharkiv.movienight.service.utils.pageable.PageableService;
import com.kharkiv.movienight.service.utils.specification.CustomSpecification;
import com.kharkiv.movienight.service.utils.specification.SearchCriteria;
import com.kharkiv.movienight.service.utils.specification.SearchOperation;
import com.kharkiv.movienight.service.utils.validation.type.MethodType;
import com.kharkiv.movienight.service.utils.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.event.EventFindDto;
import com.kharkiv.movienight.transport.dto.movie.MovieCreateDto;
import com.kharkiv.movienight.transport.dto.movie.MovieFindDto;
import com.kharkiv.movienight.transport.dto.movie.MovieOutcomeDto;
import com.kharkiv.movienight.transport.dto.movie.MovieUpdateDto;
import com.kharkiv.movienight.transport.dto.pageable.PageableDto;
import com.kharkiv.movienight.transport.mapper.movie.MovieMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Setter(onMethod_ = @Autowired)
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private MovieMapper movieMapper;
    private Validator<Movie> validator;

    private final String IMAGE_PATH = "./mn-app/src/main/resources/static/images";

    @Override
    public Long create(MovieCreateDto dto) {
        User actor = getActorFromContext();

        validator.validate(MethodType.CREATE, dto, null);

        Movie movie = movieMapper.toEntity(dto);
        uploadFileToFileSystem(movie.getImage());
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

        if(!Arrays.equals(movie.getImage(),dto.getImage())){
            uploadFileToFileSystem(movie.getImage());
        }
        return movieMapper.toEntity(dto, movie).getId();
    }

    private void uploadFileToFileSystem(byte[] file) {
        if (file != null) {
            try {
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(IMAGE_PATH)));
                stream.write(file);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
