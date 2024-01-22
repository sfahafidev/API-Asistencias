package com.assist.control.exceptions;

import com.assist.control.exceptions.errors.ApiError;

public class BusinessRunTimeException extends BaseRunTimeException {

    public BusinessRunTimeException(ApiError apiError) {
        super(apiError);
    }
}
