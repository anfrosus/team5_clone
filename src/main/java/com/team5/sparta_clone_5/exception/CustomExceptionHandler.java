package com.team5.sparta_clone_5.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandle(CustomException e) {

        return ErrorResponse.toResponseEntity(e);
    }
    // test
    @RequiredArgsConstructor
    @Getter
    @Builder
    private static class ErrorResponse {
        private final int httpStatus;
        private final String errorCode;
        private final String field;
        private final String message;

        public static ResponseEntity toResponseEntity(CustomException e) {
            return ResponseEntity
                    .status(e.getErrorCode().getHttpStatus())
                    .body(ErrorResponse.builder()
                            .errorCode(e.getErrorCode().getErrorCode())
                            .field(e.getField())
                            .message(e.getErrorCode().getMessage())
                            .build()
                    );
        }
    }
}

