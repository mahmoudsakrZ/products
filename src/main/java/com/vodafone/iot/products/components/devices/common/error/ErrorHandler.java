package com.vodafone.iot.products.components.devices.common.error;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import com.vodafone.iot.products.components.devices.common.error.codes.ApiErrorCode;
import com.vodafone.iot.products.components.devices.common.error.exceptions.ServiceException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        // Get all errors
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.toList());

        body.put("code", ApiErrorCode.MISSING_BODY_FIELD.getCode());
        body.put("message", ApiErrorCode.MISSING_BODY_FIELD.name());
        body.put("description", ApiErrorCode.MISSING_BODY_FIELD.getMessage()+ ": Required: " +errors.toString());
        return new ResponseEntity<>(body, ApiErrorCode.MISSING_BODY_FIELD.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorCode errorCode = ApiErrorCode.MISSING_QUERY_PARAM;
        return new ResponseEntity<>(errorCode.toResponseEntity(), errorCode.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorCode errorCode = ApiErrorCode.INVALID_PARAM_VALUE;
        String detailedMessage = ex.getMessage();
        return new ResponseEntity<>(errorCode.toResponseEntity(detailedMessage), errorCode.getHttpStatus());
    }

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<ApiError> handleServiceException(ServiceException exception) {
        ApiErrorCode errorCode = exception.getApiErrorCode();
        String detailedMessage = exception.getMessage();
        return new ResponseEntity<>(errorCode.toResponseEntity(detailedMessage), errorCode.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException exception) {
        ApiErrorCode errorCode = ApiErrorCode.INVALID_PARAM_VALUE;
        String detailedMessage = exception.getMessage();
        return new ResponseEntity<>(errorCode.toResponseEntity(detailedMessage!=null?detailedMessage:""), errorCode.getHttpStatus());
    }
}
