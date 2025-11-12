package com.legalsight.speechmanagementapi.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.legalsight.speechmanagementapi.exception.ErrorContants;
import com.legalsight.speechmanagementapi.exception.ExceptionResponse;
import com.legalsight.speechmanagementapi.exception.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        String errorCode = "SPH999";

        if (!ex.getBindingResult().getFieldErrors().isEmpty()) {
            var firstError = ex.getBindingResult().getFieldErrors().get(0);
            errorCode = firstError.getDefaultMessage();
        }

        log.error("Exception occurred. {} - {}", errorCode, ErrorContants.getMessage(errorCode));
        return new ResponseEntity<>(ExceptionResponse.builder()
                .errorCode(errorCode)  // a standard code for validation failures
                .errorMessage(ErrorContants.getMessage(errorCode))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        String errorCode = "SPH999";

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidFormatEx) {
            errorCode = "SPH005";
        }
        log.error("Exception occurred. {} - {}", errorCode, ErrorContants.getMessage(errorCode));
        return new ResponseEntity<>(ExceptionResponse.builder()
                .errorCode(errorCode)  // a standard code for validation failures
                .errorMessage(ErrorContants.getMessage(errorCode))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleException(GenericException ex) {
        log.error("Exception occurred. {} - {}", ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(ExceptionResponse.builder()
                .errorCode(ex.getCode())
                .errorMessage(ex.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
        log.error("Unexpected exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ExceptionResponse.builder()
                .errorCode("SPH999")
                .errorMessage("An unexpected error occurred. Please contact support.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
