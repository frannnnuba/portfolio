package com.store.api.Handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.store.api.BussinesExceptions.EmptyCartException;
import com.store.api.BussinesExceptions.OutOfStockException;
import com.store.api.BussinesExceptions.ProductNotFoundException;
import com.store.api.DTOs.APIError;

@ControllerAdvice
public class HandlerForBussinesExceptions {

    private ResponseEntity<APIError> buildError(String message, HttpStatus status) {
        return ResponseEntity.status(status)
            .body(new APIError(message, status.value(), LocalDateTime.now()));
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<APIError> emptyCartHandler(EmptyCartException ece){
        return buildError(ece.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<APIError> ProductNotFoundHandler(ProductNotFoundException ece){
        return buildError(ece.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<APIError> OutOfStockHandler(OutOfStockException ece){
        return buildError(ece.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleGeneric(Exception e) {
        return buildError("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
