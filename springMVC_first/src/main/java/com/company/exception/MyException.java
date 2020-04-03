package com.company.exception;

public class MyException extends Exception {
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public MyException(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
