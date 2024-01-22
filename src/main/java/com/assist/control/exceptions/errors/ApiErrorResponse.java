package com.assist.control.exceptions.errors;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiErrorResponse {

    private String uri;
    private LocalDateTime timeStamp;
    private ApiError apiError;

    public ApiErrorResponse(String uri, LocalDateTime timeStamp, ApiError apiError) {
        this.uri = uri;
        this.timeStamp = timeStamp;
        this.apiError = apiError;
    }

}
