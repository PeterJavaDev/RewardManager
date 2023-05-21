package com.reward.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "No resource found",
                new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }

    @ExceptionHandler(PurchasesNotFoundException.class)
    public final ResponseEntity<Object> handleNoPurchasesFoundException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "No purchases found for given criteria",
                new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }

}
