package com.ardi.server.controller.common;

import com.ardi.server.dto.common.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdviser {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseStatus<Boolean>> handleException(IllegalArgumentException e) {

        ResponseStatus<Boolean> res = ResponseStatus.failedBoolean(HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(res);
    }
}
