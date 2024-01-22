package com.assist.control.exceptions.errors;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ApiErrorResponse {

    private String uri;
    private LocalDateTime timeStamp;
    private List<ApiError> error;

    public ApiErrorResponse(String uri, LocalDateTime timeStamp, List<ApiError> error) {
        this.uri = uri;
        this.timeStamp = timeStamp;
        this.error = error;
    }

    public void addError(ApiError error) {
        if (Objects.isNull(this.error)) {
            this.error = new ArrayList<>();
        }
        this.error.add(error);
    }

    public static ApiErrorResponse createResponseWithError(String uri, ApiError error) {
        return new ApiErrorResponse(uri, LocalDateTime.now(), List.of(error));
    }

    public static ApiErrorResponse createResponseWithListErrors(String uri, List<ApiError> errors) {
        return new ApiErrorResponse(uri, LocalDateTime.now(), errors);
    }

}
