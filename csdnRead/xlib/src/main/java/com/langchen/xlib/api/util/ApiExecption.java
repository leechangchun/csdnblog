package com.langchen.xlib.api.util;

/**
 * Created by admin on 2016/8/18.
 */
public class ApiExecption extends RuntimeException {
    private String request;
    private String error_code;
    private String error;

    public ApiExecption() {
    }

    public ApiExecption(String request, String error_code, String error) {
        this.request = request;
        this.error_code = error_code;
        this.error = error;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Api异常 {" +
                "request='" + request + '\'' +
                ", error_code='" + error_code + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
