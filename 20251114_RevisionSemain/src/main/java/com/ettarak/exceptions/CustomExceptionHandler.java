package com.ettarak.exceptions;

import com.ettarak.responses.HttpResponse;
import com.ettarak.utils.DateTimeFormatter;
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

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<HttpResponse<?>> createHttpResponse(HttpStatus status,
                                               String message,
                                               Exception exception)
    {
        log.error(exception.getMessage());

        return new ResponseEntity<>(
                HttpResponse.builder()
                        .errorMessage(message)
                        .developerMessage(exception.getMessage())
                        .status(status)
                        .statusCode(status.value())
                        .timStamp(LocalDateTime.now().format(DateTimeFormatter.formatter))
                        .build()
                , status
        );
    }


    @ExceptionHandler(EmployeeNotFound.class)
    public ResponseEntity<HttpResponse<?>> handleEmployeeNotFound(EmployeeNotFound exception) {
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<HttpResponse<?>> handleException(Exception exception) {
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        log.error(exception.getMessage());


        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                    HttpResponse.builder()
                            .errorMessage(errorMessage)
                            .developerMessage(exception.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(status.value())
                            .timStamp(LocalDateTime.now().format(DateTimeFormatter.formatter))
                            .build()
                , status);
    }

}
