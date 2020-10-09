package com.kharkiv.movienight.service.validation.model;

import com.kharkiv.movienight.exception.movie.*;
import com.kharkiv.movienight.exception.standard.BadRequestException;
import com.kharkiv.movienight.persistence.model.movie.Movie;
import com.kharkiv.movienight.persistence.model.user.User;
import com.kharkiv.movienight.persistence.repository.MovieRepository;
import com.kharkiv.movienight.service.validation.type.MethodType;
import com.kharkiv.movienight.service.validation.validator.Validator;
import com.kharkiv.movienight.transport.dto.movie.MovieCreateDto;
import com.kharkiv.movienight.transport.dto.movie.MovieUpdateDto;
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
        User actor = getActorFromContext();
        Object dto;
        Object object;
        Movie movie = null;

        if (objects.length == 1) {
            object = objects[0];

            if (object instanceof Movie) {
                movie = (Movie) object;
            }
            executeValidation(methodType, actor, null, movie);

        } else if (objects.length == 2) {
            dto = objects[0];
            object = objects[1];

            if (object instanceof Movie) {
                movie = (Movie) object;
            }
            executeValidation(methodType, actor, dto, movie);

        } else {
            executeValidation(methodType, actor, null, null);
        }
    }

    public void executeValidation(MethodType methodType, User actor, Object dto, Movie movie) {
        switch (methodType) {
            case CREATE:
                validateCreate(dto);
                break;
            case UPDATE:
                validateUpdate(dto, movie);
                break;
            default:
                throw new BadRequestException("Incorrect METHOD_TYPE");
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
            validateTrailerUrl(movieDto.getTrailer());
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
            validateTrailerUrl(movieDto.getTrailer());
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
        if(rating != null && (rating <= 0.0 || rating > 10.0)){
            throw new MovieRatingException();
        }
    }

    private void validateAge(Integer age) {
        if(age != null && (age <= 0 || age > 21)) {
            throw new MovieDurationException();
        }
    }

    private void validateTrailerUrl(String trailer) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        if (trailer == null){
            return;
        }

        if(!trailer.matches(regex)){
            throw new MovieTrailerUrlException();
        }
    }
}
