package com.example.shoe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Khởi tạo logger để ghi nhận các ngoại lệ
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Xử lý các ngoại lệ runtime
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> handlingRuntimeException(RuntimeException exception) {
        logger.error("Runtime Exception: ", exception);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Xử lý các ngoại lệ liên quan đến validate
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlingValidation(MethodArgumentNotValidException exception) {
        logger.error("Validation Error: ", exception);
        Map<String, String> errors = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResponseEntity.badRequest().body(errors);
    }
}
