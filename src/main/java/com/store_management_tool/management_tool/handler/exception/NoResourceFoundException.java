package com.store_management_tool.management_tool.handler.exception;

import com.store_management_tool.management_tool.common.Errors;

public class NoResourceFoundException extends AppException{
    public NoResourceFoundException() {
        super(Errors.NO_RESOURCE_FOUND.getStatus(),Errors.NO_RESOURCE_FOUND.getMessage());
    }
}
