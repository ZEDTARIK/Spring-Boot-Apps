package com.ettarak.exceptions;

import com.ettarak.models.HttpResponse;
import com.ettarak.utils.DateFormatter;
import jakarta.annotation.Nullable;
import jakarta.persistence.NoResultException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class HandleException extends ResponseEntityExceptionHandler {

    private ResponseEntity<HttpResponse<?>> createHttpErrorResponse(HttpStatus status,
                                                              String reason,
                                                              Exception exception)
    {
        log.error(exception.getMessage());

        return new ResponseEntity<>(
            HttpResponse.builder()
                        .reason(reason)
                        .developerMessage(exception.getMessage())
                        .status(status)
                        .statusCode(status.value())
                        .timeStamp(LocalDateTime.now().format(DateFormatter.FORMATTER))
                    .build()
                , status
        );
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<HttpResponse<?>> noteNotFoundException(NoteNotFoundException exception)
    {
        return createHttpErrorResponse(NOT_FOUND, exception.getMessage(), exception);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse<?>> noResultException(NoResultException exception) {
        return createHttpErrorResponse(NOT_FOUND, exception.getMessage(), exception);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<HttpResponse<?>> servletException(ServletException exception) {
        return createHttpErrorResponse(INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse<?>> exception(Exception exception) {
        return createHttpErrorResponse(INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
    }



    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(exception.getMessage());

        return  new ResponseEntity<>(
                HttpResponse.builder()
                        .reason(exception.getMessage())
                        .developerMessage(exception.getMessage())
                        .status(HttpStatus.valueOf(status.value()))
                        .statusCode(status.value())
                        .timeStamp(LocalDateTime.now().format(DateFormatter.FORMATTER))
                        .build()
                ,status
        );
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request)
    {
        log.error(exception.getMessage());

        String reason = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                HttpResponse.builder()
                        .reason(reason)
                        .developerMessage(exception.getMessage())
                        .status(HttpStatus.valueOf(status.value())) //BAD_REQUEST
                        .statusCode(status.value())
                        .timeStamp(LocalDateTime.now().format(DateFormatter.FORMATTER))
                        .build()
                , status
        );

    }



}
