package com.fairyland.xrobot.modular.xrobot.exception;

/**
 * @program: smartAuth->XRobotException
 * @description: TODO
 * @author: ctc
 * @create: 2019-11-11 22:48
 **/
public class XRobotException extends RuntimeException {

    private int errorCode;
    private String errorMessage;

    public XRobotException() {
        super();
    }

    public XRobotException(int errorCode) {
        this.errorCode = errorCode;
    }

    public XRobotException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
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
