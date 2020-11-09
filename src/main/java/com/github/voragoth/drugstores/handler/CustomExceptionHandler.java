package com.github.voragoth.drugstores.handler;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, ServletWebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }

        ApiError body = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequest().getRequestURI())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(String.join(",", errors))
                .build();
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, ServletWebRequest request) {

        ApiError body = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequest().getRequestURI())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getCause().getLocalizedMessage())
                .build();

        if (ex.getCause() instanceof NumberFormatException) {
            body.setMessage(StringUtils.split(ex.getCause().getLocalizedMessage(), ".")[0]);
            return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else {
            String requiredType;
            if(ex.getRequiredType() != null) {
                requiredType = Objects.requireNonNull(ex.getRequiredType()).getName();
            } else {
                requiredType = StringUtils.EMPTY + ex.getRequiredType();
            }
                body.setMessage(ex.getName() + " should be of type " + requiredType);

        }
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Data
    @Builder
    public static class ApiError {

        private LocalDateTime timestamp;

        private Integer status;

        private String message;

        private String error;

        private String path;
    }
}
