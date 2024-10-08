package com.store_management_tool.management_tool.handler;

import com.store_management_tool.management_tool.handler.exception.AppException;
import com.store_management_tool.management_tool.handler.exception.NoResourceFoundException;
import com.store_management_tool.management_tool.handler.exception.NoValidProductException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NoValidProductException.class)
    public ResponseEntity<Object> handleNoValidProductException(NoValidProductException exception) {
        return new ResponseEntity<>(createErrorStructure(exception), exception.getStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException exception) {
        return new ResponseEntity<>(createErrorStructure(exception), exception.getStatus());
    }
    private Map<String, Object> createErrorStructure(AppException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Message", exception.getMessage());
        body.put("Status", exception.getStatus());
        return body;
    }
}
