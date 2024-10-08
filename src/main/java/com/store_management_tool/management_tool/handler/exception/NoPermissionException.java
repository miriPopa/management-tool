package com.store_management_tool.management_tool.handler.exception;

import com.store_management_tool.management_tool.common.Errors;

public class NoPermissionException extends AppException {
    public NoPermissionException() {
        super(Errors.NO_PERMISSIION.getStatus(),Errors.NO_PERMISSIION.getMessage());
    }
}
