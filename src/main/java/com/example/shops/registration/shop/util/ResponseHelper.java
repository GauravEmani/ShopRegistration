package com.example.shops.registration.shop.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {

    public final static String HEADER_ERROR_MESSAGE = "ErrorMessage";
    public final static String HEADER_EXCEPTION_MESSAGE = "ExceptionMessage";
    public final static String HEADER_EXCEPTION_MESSAGE_NOT_PROVIDED = "Not provided";

    public static <T> ResponseEntity<T> ok(T obj) {
        return ResponseEntity.ok(obj);
    }

    public static <T> ResponseEntity<T> error(HttpStatus httpStatus, String errorMessage, Exception exception, T obj) {
        return ResponseEntity.status(httpStatus)
                .header(HEADER_ERROR_MESSAGE, errorMessage)
                .header(HEADER_EXCEPTION_MESSAGE, exception != null ? exception.getMessage() : HEADER_EXCEPTION_MESSAGE_NOT_PROVIDED)
                .body(obj);
    }

    public static <T> ResponseEntity<T> error(HttpStatus httpStatus, String errorMessage, Exception exception) {
        return error(httpStatus, errorMessage, exception, null);
    }

    public static <T> ResponseEntity<T> error(HttpStatus httpStatus, String errorMessage) {
        return error(httpStatus, errorMessage, null);
    }
    
    public static <T> ResponseEntity<T> info(HttpStatus httpStatus, String infoMessage, Object obj) {
    	return info(httpStatus, infoMessage, obj);
    }
}
