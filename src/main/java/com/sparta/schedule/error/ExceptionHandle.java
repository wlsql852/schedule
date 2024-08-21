package com.sparta.schedule.error;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandle {
    /*
     * 8단계 ExceptionHandel를 이용한 예외 처리
     * */
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity illegalExHandle(IllegalAccessException e) {
        log.error("IllegalAccessException", e.getMessage());
        return new ResponseEntity(new IllegalExceptionMsg(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity illegalExHandle(ValidationException e) {
        log.error("ValidationException", e.getMessage());
        return new ResponseEntity(new ValidateExceptionMsg(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
