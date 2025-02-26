package com.Backend.ProfileMs.Utils;

import com.Backend.ProfileMs.Exception.HsException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorInfo>exceptionHandler(Exception ex){
        String message="Some error occured";
        Integer status= HttpStatus.INTERNAL_SERVER_ERROR.value();
        LocalDateTime timeStamp=LocalDateTime.now();
        ErrorInfo errorInfo=new ErrorInfo(message,status,timeStamp);
        return new ResponseEntity<>(errorInfo,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HsException.class)
    public ResponseEntity<ErrorInfo> hsExceptionHandler(HsException ex) {
        System.out.println("Handling HsException: " + ex.getMessage());
        String message = ex.getMessage();
        Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        LocalDateTime timeStamp = LocalDateTime.now();
        ErrorInfo errorInfo = new ErrorInfo(message, status, timeStamp);
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> handleValidationException(Exception ex) {
        String errorMessage;

        if (ex instanceof MethodArgumentNotValidException manv) {
            errorMessage = manv.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        } else if (ex instanceof ConstraintViolationException cve) {
            errorMessage = cve.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
        } else {
            errorMessage = "An unexpected validation error occurred.";
        }

        ErrorInfo errorInfo = new ErrorInfo(errorMessage, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }


}
