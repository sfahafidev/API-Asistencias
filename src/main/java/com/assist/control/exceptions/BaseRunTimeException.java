package com.assist.control.exceptions;

import com.assist.control.exceptions.errors.ApiError;
import lombok.Getter;

@Getter
public class BaseRunTimeException extends RuntimeException {

    private final ApiError apiError;

    public BaseRunTimeException(ApiError apiError) {
        this.apiError = apiError;
    }

}
