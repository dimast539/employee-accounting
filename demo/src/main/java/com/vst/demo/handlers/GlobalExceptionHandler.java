package com.vst.demo.handlers;

import com.vst.demo.exceptions.PersonNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> personNotFoundException(PersonNotFoundException personNotFoundException){
        Map<String, String> response = new HashMap<>();
        response.put("error", personNotFoundException.getMessage());


        return response;
    }

}
