package com.example.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ErrorHanlingController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity onException(Exception e){
        log.error(e.getMessage());
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
