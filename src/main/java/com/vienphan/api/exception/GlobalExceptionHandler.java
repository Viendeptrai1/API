package com.vienphan.api.exception;

import com.vienphan.api.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(
            new ApiResponse(false, "Dữ liệu không hợp lệ", errors), 
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ApiResponse> handleStorageException(StorageException ex) {
        return new ResponseEntity<>(
            new ApiResponse(false, "Lỗi lưu trữ file: " + ex.getMessage(), null), 
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<ApiResponse> handleStorageFileNotFound(StorageFileNotFoundException ex) {
        return new ResponseEntity<>(
            new ApiResponse(false, "Không tìm thấy file: " + ex.getMessage(), null), 
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return new ResponseEntity<>(
            new ApiResponse(false, "File quá lớn. Kích thước tối đa cho phép là 10MB", null), 
            HttpStatus.PAYLOAD_TOO_LARGE
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        return new ResponseEntity<>(
            new ApiResponse(false, "Lỗi hệ thống: " + ex.getMessage(), null), 
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
