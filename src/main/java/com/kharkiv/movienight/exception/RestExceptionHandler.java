package com.kharkiv.movienight.exception;

import com.kharkiv.movienight.exception.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(UserWithSuchEmailAlreadyExists.class)
    public ResponseEntity<Object> handleUserWithSuchEmailAlreadyExists(UserWithSuchEmailAlreadyExists exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Object> handlePasswordMismatchException(PasswordMismatchException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithSuchPhoneAlreadyExists.class)
    public ResponseEntity<Object> handleUserWithSuchPhoneAlreadyExists(UserWithSuchPhoneAlreadyExists exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithSuchUserNameAlreadyExists.class)
    public ResponseEntity<Object> handleUserWithSuchUserNameAlreadyExists(UserWithSuchUserNameAlreadyExists exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDateOfBirthInvalidException.class)
    public ResponseEntity<Object> handleUserDateOfBirthInvalidException(UserDateOfBirthInvalidException exception, WebRequest request) {
        return getObjectResponseEntity(request, exception.getMessage(), HttpStatus.BAD_REQUEST);
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
