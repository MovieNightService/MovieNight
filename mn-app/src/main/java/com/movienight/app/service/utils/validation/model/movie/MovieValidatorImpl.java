package com.movienight.app.service.utils.validation.model.movie;

import com.movienight.app.exception.movie.MovieDurationException;
import com.movienight.app.exception.movie.MovieIssueInvalidException;
import com.movienight.app.exception.movie.MovieRatingException;
import com.movienight.app.exception.movie.MovieWithSuchNameAlreadyExistsException;
import com.movienight.app.exception.common.BadRequestException;
import com.movienight.app.service.utils.validation.type.MethodType;
import com.movienight.app.service.utils.validation.validator.Validator;
import com.movienight.foundation.dto.movie.MovieCreateDto;
import com.movienight.foundation.dto.movie.MovieUpdateDto;
import com.movienight.foundation.persistence.model.movie.Movie;
import com.movienight.foundation.persistence.repository.MovieRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
@Setter(onMethod_ = @Autowired)
public class MovieValidatorImpl implements Validator<Movie> {

    private MovieRepository movieRepository;

    @Override
    public void validate(MethodType methodType, Object... objects) {
        Object dto;
        Object object;
        Movie movie = null;

        if (objects.length == 1) {
            object = objects[0];

            if (object instanceof Movie) {
                movie = (Movie) object;
            }
            executeValidation(methodType, null, movie);

        } else if (objects.length == 2) {
            dto = objects[0];
            object = objects[1];

            if (object instanceof Movie) {
                movie = (Movie) object;
            }
            executeValidation(methodType, dto, movie);

        } else {
            executeValidation(methodType, null, null);
        }
    }

    public void executeValidation(MethodType methodType, Object dto, Movie movie) {
        switch (methodType) {
            case CREATE -> validateCreate(dto);
            case UPDATE -> validateUpdate(dto, movie);
            case DELETE -> validateDelete(movie);
            default -> throw new BadRequestException("Incorrect METHOD_TYPE");
        }
    }

    private void validateDelete(Movie movie) {
        if (!movie.getEvents().isEmpty()) {
            throw new BadRequestException("Movie used in Events");
        }
    }

    private void validateCreate(Object dto) {
        MovieCreateDto movieDto = null;

        if (dto instanceof MovieCreateDto) {
            movieDto = (MovieCreateDto) dto;
        }

        if (movieDto != null) {
            validateExistsByName(movieDto.getName());
            validateDuration(movieDto.getDuration());
//            validateIssue(movieDto.getIssue());
            validateRating(movieDto.getRating());
            validateAge(movieDto.getAge());
        } else {
            throw new BadRequestException("DTO was not get or his type is incorrect");
        }
    }

    private void validateUpdate(Object dto, Movie movie) {
        MovieUpdateDto movieDto = null;

        if (dto instanceof MovieUpdateDto) {
            movieDto = (MovieUpdateDto) dto;
        }

        if (movieDto != null) {
            validateExistsByNameNotId(movieDto.getName(), movie.getId());
            validateDuration(movieDto.getDuration());
//            validateIssue(movieDto.getIssue());
            validateRating(movieDto.getRating());
            validateAge(movieDto.getAge());
        } else {
            throw new BadRequestException("DTO was not get or his type is incorrect");
        }
    }

    private void validateExistsByName(String name) {
        if (movieRepository.existsByName(name)) {
            throw new MovieWithSuchNameAlreadyExistsException();
        }
    }

    private void validateExistsByNameNotId(String name, Long id) {
        if (movieRepository.existsByNameAndIdNot(name, id)) {
            throw new MovieWithSuchNameAlreadyExistsException();
        }
    }

    private void validateDuration(Integer duration) {
        if(duration <= 0) {
            throw new MovieDurationException();
        }
    }

    private void validateIssue(Instant issue) {
        Instant maxDate = LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant minDate = LocalDate.parse("1920-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant();

        int minDateResult = issue.compareTo(minDate);
        int maxDateResult = issue.compareTo(maxDate);

        boolean isValidDateOfBirth = (minDateResult < 0 && maxDateResult > 0);

        if (!isValidDateOfBirth) {
            throw new MovieIssueInvalidException();
        }
    }

    private void validateRating(Double rating) {
        if (rating != null && (rating <= 0.0 || rating > 10.0)) {
            throw new MovieRatingException();
        }
    }

    private void validateAge(Integer age) {
        // TODO: 10/10/20  age may be different It depend on a film and why here is MovieDurationException
        if (age != null && (age <= 0 || age > 21)) {
            throw new MovieDurationException();
        }
    }
}
