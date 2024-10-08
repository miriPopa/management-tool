package com.store_management_tool.management_tool.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class AppException extends RuntimeException {
    private HttpStatus status;
    private String message;
}