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

/**
 * Clase que contiene los manejos excepcionales no cubiertos por spring por defecto, o cubiertos sin respuesta clara.
 *
 * @author Manuel Vasquez Cruz.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Metodo que intercepta cuando se lanza la excepcion ConstraintViolationException, usualmente se lanza cuando
     * no se cumple alguna validacion de las entradas en un Cotroller.
     *
     * @param ex      la excepcion.
     * @param request el request.
     * @return la respuesta en un formato legible.
     */
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

    /**
     * Metodo que intercepta cuando se lanza la excepcion MethodArgumentTypeMismatchException, usualmente se lanza
     * cuando no se puede convertir un dato a lo solicitado en un controlador.
     *
     * @param ex      la excepcion.
     * @param request el request.
     * @return la respuesta en un formato legible.
     */
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
            if (ex.getRequiredType() != null) {
                requiredType = Objects.requireNonNull(ex.getRequiredType()).getName();
            } else {
                requiredType = StringUtils.EMPTY + ex.getRequiredType();
            }
            body.setMessage(ex.getName() + " should be of type " + requiredType);

        }
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Clase para mostrar el error a la salida, cumple el mismo formato que lo lanzado por ResponseStatusException.
     *
     * @author Manuel Vasquez Cruz.
     * @see org.springframework.web.server.ResponseStatusException
     */
    @Data
    @Builder
    public static class ApiError {

        /**
         * Marca de tiempo en que se lanza la excepcion.
         */
        private LocalDateTime timestamp;

        /**
         * Estado http usualmente quie se lanza la excepcion
         */
        private Integer status;

        /**
         * Mensaje de la excepcion.
         */
        private String message;

        /**
         * Descripcion del error http.
         */
        private String error;

        /**
         * Ruta donde se lanzo el error.
         */
        private String path;
    }
}
