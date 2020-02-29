package com.icd.wksh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<?> handleRequestException(CustomException e){
        CustomExceptionObject object = new CustomExceptionObject("Fail", e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
