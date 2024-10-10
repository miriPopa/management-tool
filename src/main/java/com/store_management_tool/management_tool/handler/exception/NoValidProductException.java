package com.store_management_tool.management_tool.handler.exception;

import com.store_management_tool.management_tool.common.exceptions.Errors;

public class NoValidProductException extends AppException{
    public NoValidProductException() {
        super(Errors.NO_VALID_PRODUCT.getStatus(),Errors.NO_VALID_PRODUCT.getMessage());
    }
}
