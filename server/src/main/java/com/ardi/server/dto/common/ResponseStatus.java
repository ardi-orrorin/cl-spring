package com.ardi.server.dto.common;

import org.springframework.http.HttpStatus;

public record ResponseStatus<T>(
    String message,
    HttpStatus status,
    T data
) {
    public static ResponseStatus<Boolean> successBoolean(String message) {
        return new ResponseStatus<>(message, HttpStatus.OK, true);
    }

    public static ResponseStatus<Boolean> failedBoolean(HttpStatus status, String message) {
        return new ResponseStatus<>(message, status, false);
    }

    public static <T> ResponseStatus<T> success(String message, T data) {
        return new ResponseStatus<>(message, HttpStatus.OK, data);
    }

    public static <T> ResponseStatus<T> failed(HttpStatus status, String message, T data) {
        return new ResponseStatus<>(message, status, data);
    }
}
