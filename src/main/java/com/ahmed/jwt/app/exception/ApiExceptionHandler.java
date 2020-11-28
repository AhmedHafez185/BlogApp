/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahmed.jwt.app.exception;

import java.util.ArrayList;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Ahmed Hafez
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiBaseException.class})
    public ResponseEntity<ErrorDetails> handleException(ApiBaseException ex, WebRequest request) {
        ErrorDetails details = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details, ex.getStatusCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationError> handleEntityExceptionValidation(ConstraintViolationException ex, WebRequest request) {
        ValidationError validErrors = new ValidationError();
        ArrayList<ObjectError> objErrors = new ArrayList<>();
        for (ConstraintViolation<?> err : ex.getConstraintViolations()) {

            ObjectError objError = new ObjectError(err.getPropertyPath().toString(), err.getMessageTemplate());
            objErrors.add(objError);
        }
        validErrors.setErrors(objErrors);
        validErrors.setUri(request.getDescription(false));
        return new ResponseEntity<>(validErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CustomValidationException.class})
    public ResponseEntity<ValidationError> handleDtoExceptionValidation(CustomValidationException ex, WebRequest request) {
        ValidationError validErrors = new ValidationError();
        validErrors.setErrors(ex.handleValidation());
        validErrors.setUri(request.getDescription(false));
        return new ResponseEntity<>(validErrors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails details = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(details, HttpStatus.NOT_IMPLEMENTED);
    }
}
