package com.team5.sparta_clone_5.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    String field;
    ErrorCode errorCode;
}