package com.accenture.java.msopentdmaven.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(Exception exception) {
        BadRequestException raisedException = (BadRequestException) exception;
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                raisedException.getErrorId(),
                raisedException.getErrorMessage(),
                raisedException.getErrorDetails()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
