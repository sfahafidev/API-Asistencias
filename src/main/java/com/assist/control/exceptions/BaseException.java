package com.assist.control.exceptions;

import com.assist.control.exceptions.errors.ApiError;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ApiError apiError;

    public BaseException(ApiError apiError) {
        this.apiError = apiError;
    }

}
