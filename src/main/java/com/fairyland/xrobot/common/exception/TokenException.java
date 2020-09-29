package com.fairyland.xrobot.common.exception;

/**
 * @program: fairyland->TokenException
 * @description: TODO
 * @author: ctc
 * @create: 2019-11-28 18:05
 **/
public class TokenException extends RuntimeException {

    private int errorCode;
    private String errorMessage;

    public TokenException() {
        super();
    }

    public TokenException(int errorCode) {
        this.errorCode = errorCode;
    }

    public TokenException(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}


