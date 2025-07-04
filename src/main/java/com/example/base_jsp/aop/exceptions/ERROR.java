package com.example.base_jsp.aop.exceptions;

import lombok.Getter;

@Getter
public enum ERROR {

    SUCCESS(200, "Success"),
    INVALID_REQUEST(100, "Request không hợp lệ"),
    SYSTEM_ERROR(99, "Hệ thống đang nâng cấp tính năng này, xin vui lòng thử lại sau!"),
    BAD_REQUEST(400, "Bad request"),
    RESOURCE_NOT_FOUND(404, "Resource not found"),
    DUPLICATE_RESOURCE(409, "Duplicate resource"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    UNAUTHORIZED(401, "UNAUTHORIZED");

    private int code;
    private String msg;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ERROR(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiException ERR() {
        return new ApiException(this);
    }
}
