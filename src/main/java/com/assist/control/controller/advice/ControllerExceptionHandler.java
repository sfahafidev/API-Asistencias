package com.assist.control.controller.advice;

import com.assist.control.exceptions.BaseRunTimeException;
import com.assist.control.exceptions.BusinessRunTimeException;
import com.assist.control.exceptions.errors.ApiError;
import com.assist.control.exceptions.errors.ApiErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import static org.springframework.http.HttpStatus.*;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ControllerExceptionHandler.class);
    private static final String STACK_TRACE = "-- Pila de llamadas --";
    private static final String FIELD = "Campo ";


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public ApiErrorResponse globalExceptionHandler(Exception ex, ServletWebRequest request) {
        var apiError = new ApiError("Exception", getGenericErrorMessageAndCause(ex));

        logger.error(ex.getMessage());
        logger.trace(STACK_TRACE, ex.getStackTrace());

        List<ApiError> errors = new ArrayList<>();
        errors.add(apiError);
        return ApiErrorResponse.createResponseWithListErrors(request.getRequest().getRequestURI(), errors);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = NOT_IMPLEMENTED)
    public ApiErrorResponse runTimeExceptionHandler(RuntimeException ex, ServletWebRequest request) {
        var apiError = new ApiError("RunTimeException", getGenericErrorMessageAndCause(ex));

        logger.error(ex.getMessage());
        logger.trace(STACK_TRACE, ex.getStackTrace());

        List<ApiError> errors = new ArrayList<>();
        errors.add(apiError);
        return ApiErrorResponse.createResponseWithListErrors(request.getRequest().getRequestURI(), errors);
    }

    @ExceptionHandler(BusinessRunTimeException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public ApiErrorResponse businessExceptionHandler(BusinessRunTimeException ex, ServletWebRequest request) {
        return getApiErrorResponse(ex, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public ApiErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, ServletWebRequest request) {
        List<ApiError> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();

            ApiError apiError = new ApiError(fieldName, msg, FIELD + fieldName + ": " + msg);
            errors.add(apiError);
        });

        logger.error("Controller advice: argumentNotValidException. Error: {}", ex.getMessage());
        logger.trace(STACK_TRACE, ex.getStackTrace());

        return ApiErrorResponse.createResponseWithListErrors(request.getRequest().getRequestURI(), errors);
    }

    private ApiErrorResponse getApiErrorResponse(BaseRunTimeException ex, ServletWebRequest request) {
        logger.error(ex.getApiError().getLogMessage());
        List<ApiError> errors = new ArrayList<>();
        errors.add(ex.getApiError());
        return ApiErrorResponse.createResponseWithListErrors(request.getRequest().getRequestURI(), errors);
    }

    private String getGenericErrorMessageAndCause(Exception ex) {
        if (ex.getCause() != null) {
            return ex.getCause().toString() + " " + ex.getMessage();
        }

        return ex.getMessage();
    }

}
