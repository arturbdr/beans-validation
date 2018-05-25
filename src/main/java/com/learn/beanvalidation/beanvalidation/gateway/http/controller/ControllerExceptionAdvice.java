package com.learn.beanvalidation.beanvalidation.gateway.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.beanvalidation.beanvalidation.gateway.http.to.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity catchException(MethodArgumentNotValidException e) {
        final List<Error> validationErrors = new LinkedList<>();
        final List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        allErrors
                .stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .forEach(objectError -> validationErrors.add(getError(objectError)));
        return ResponseEntity
                .badRequest()
                .body(validationErrors);
    }

    private Error getError(FieldError fieldError) {
        return Error.builder()
                .fieldName(fieldError.getField())
                .inputValue(fieldError.getRejectedValue())
                .rejectReason(fieldError.getDefaultMessage())
                .build();
    }
}
