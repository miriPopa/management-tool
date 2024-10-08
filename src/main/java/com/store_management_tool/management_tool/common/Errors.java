package com.store_management_tool.management_tool.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Errors {

    NO_VALID_PRODUCT("No valid product", HttpStatus.BAD_REQUEST),
    NO_RESOURCE_FOUND("No resource found", HttpStatus.NOT_FOUND),
    NO_PERMISSIION("No permission", HttpStatus.FORBIDDEN);
    private String message;
    private HttpStatus status;

    Errors(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
