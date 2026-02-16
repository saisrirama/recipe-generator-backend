package com.springai.recipegen;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, Object>> handleRestClientException(RestClientException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "AI Service Error");

        String message = ex.getMessage();
        if (message != null && message.contains("401")) {
            response.put("message", "Authentication Failure: Please check your Hugging Face API key.");
        } else if (message != null && message.contains("extracting response")) {
            response.put("message",
                    "Format Error: Received unexpected response format from AI provider. This often happens due to authentication issues (401).");
        } else {
            response.put("message", "An unexpected error occurred while communicating with the AI service: " + message);
        }

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
