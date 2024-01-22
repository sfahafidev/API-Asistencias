package com.assist.control.exceptions;

import com.assist.control.exceptions.errors.ApiError;

public class BusinessException extends BaseException {

    public BusinessException(ApiError apiError) {
        super(apiError);
    }
}
