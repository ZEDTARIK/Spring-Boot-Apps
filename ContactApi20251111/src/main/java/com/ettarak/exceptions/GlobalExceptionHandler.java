package com.ettarak.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        Map<String, String> error = Map.of(
                "error", ex.getMessage(),
                "status", "500"
        );
        return ResponseEntity.status(500).body(error);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIO(IOException ex) {
        Map<String, String> error = Map.of(
                "error", "Image not found or unreadable",
                "status", "404"
        );
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<Map<String, String>> handleInvalidImage(InvalidImageException ex) {
        Map<String, String> error = Map.of(
                "error", ex.getMessage(),
                "status", "400"
        );
        return ResponseEntity.badRequest().body(error);
    }

}
