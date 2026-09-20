package br.com.devshowcase.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidation(
        MethodArgumentNotValidException ex,
        HttpServletRequest request
    ) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            fields.putIfAbsent(error.getField(), error.getDefaultMessage())
        );

        return build(
            HttpStatus.BAD_REQUEST,
            "Erro de validação",
            request.getRequestURI(),
            fields
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorDTO> handleConstraintViolation(
        ConstraintViolationException ex,
        HttpServletRequest request
    ) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation ->
            fields.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        return build(
            HttpStatus.BAD_REQUEST,
            "Erro de validação",
            request.getRequestURI(),
            fields
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleNotFound(
        ResourceNotFoundException ex,
        HttpServletRequest request
    ) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorDTO> handleResponseStatus(
        ResponseStatusException ex,
        HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        String message = ex.getReason() == null ? status.getReasonPhrase() : ex.getReason();
        return build(status, message, request.getRequestURI(), null);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ApiErrorDTO> handleBadRequest(
        Exception ex,
        HttpServletRequest request
    ) {
        return build(
            HttpStatus.BAD_REQUEST,
            "Requisição inválida ou dados em conflito",
            request.getRequestURI(),
            null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleUnexpected(
        Exception ex,
        HttpServletRequest request
    ) {
        return build(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Erro interno do servidor",
            request.getRequestURI(),
            null
        );
    }

    private ResponseEntity<ApiErrorDTO> build(
        HttpStatus status,
        String message,
        String path,
        Map<String, String> fieldErrors
    ) {
        ApiErrorDTO body = new ApiErrorDTO(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            path,
            fieldErrors
        );

        return ResponseEntity.status(status).body(body);
    }
}
