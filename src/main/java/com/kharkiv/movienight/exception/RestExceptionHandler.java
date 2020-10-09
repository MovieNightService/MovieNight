package com.kharkiv.movienight.exception;

import com.kharkiv.movienight.exception.event.EventNotFoundException;
import com.kharkiv.movienight.exception.movie.*;
import com.kharkiv.movienight.exception.user.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserWithSuchEmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserWithSuchEmailAlreadyExistsException(UserWithSuchEmailAlreadyExistsException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Object> handlePasswordMismatchException(PasswordMismatchException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithSuchPhoneAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserWithSuchPhoneAlreadyExistsException(UserWithSuchPhoneAlreadyExistsException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithSuchUserNameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserWithSuchUserNameAlreadyExistsException(UserWithSuchUserNameAlreadyExistsException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDateOfBirthInvalidException.class)
    public ResponseEntity<Object> handleUserDateOfBirthInvalidException(UserDateOfBirthInvalidException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserBadCredentialsException.class)
    public ResponseEntity<Object> handleUserBadCredentialsException(UserBadCredentialsException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDeletedException.class)
    public ResponseEntity<Object> handleUserDeletedException(UserDeletedException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Object> handleMovieNotFoundException(MovieNotFoundException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<Object> handleEventNotFoundException(EventNotFoundException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieDurationException.class)
    public ResponseEntity<Object> movieDurationException(MovieDurationException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieIssueInvalidException.class)
    public ResponseEntity<Object> movieIssueInvalidException(MovieIssueInvalidException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieRatingException.class)
    public ResponseEntity<Object> movieRatingException(MovieRatingException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieTrailerUrlException.class)
    public ResponseEntity<Object> movieTrailerUrlException(MovieTrailerUrlException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieWithSuchNameAlreadyExistsException.class)
    public ResponseEntity<Object> movieWithSuchNameAlreadyExistsException(MovieWithSuchNameAlreadyExistsException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> getObjectResponseEntity(WebRequest request, String message, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", request.getContextPath());

        return new ResponseEntity<>(body, status);
    }

}
