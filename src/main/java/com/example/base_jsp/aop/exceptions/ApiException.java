package com.example.base_jsp.aop.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {

    private int code;
    private Object data;

    public ApiException(int code) {
        this.code = code;
    }

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public ApiException(ERROR msg) {
        super(msg.getMsg());
        this.code = msg.getCode();
    }

    public ApiException(ERROR msg, Object data) {
        super(msg.getMsg());
        this.code = msg.getCode();
        this.data = data;
    }

    public ApiException(ERROR msg, String message) {
        super(message);
        this.code = msg.getCode();
    }

    public ApiException(ERROR msg, String message, Object data) {
        super(message);
        this.code = msg.getCode();
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ApiException)) {
            return false;
        } else {
            ApiException other = (ApiException)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getCode() != other.getCode()) {
                return false;
            } else {
                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ApiException;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getCode();
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        int var10000 = this.getCode();
        return "ApiException(code=" + var10000 + ", data=" + this.getData() + ")";
    }
}
