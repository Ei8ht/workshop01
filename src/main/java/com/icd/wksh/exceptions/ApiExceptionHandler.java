package com.icd.wksh.exceptions;

import com.icd.wksh.services.WorkshopTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(WorkshopTransactionService.class);

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<?> handleRequestException(RuntimeException e){
        CustomExceptionObject object = new CustomExceptionObject("Fail", e.getMessage(), LocalDateTime.now());
        log.error("Error", e);
        return new ResponseEntity<>(object, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(RuntimeException e){
        CustomExceptionObject object = new CustomExceptionObject("Request Fail", e.getMessage(), LocalDateTime.now());
        log.error("Error", e);
        return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
    }
}
