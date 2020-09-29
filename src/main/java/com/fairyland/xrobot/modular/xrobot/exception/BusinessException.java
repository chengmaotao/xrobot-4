package com.fairyland.xrobot.modular.xrobot.exception;

/**
 * @Description: BusinessException
 * @Author wmg
 * @Version V1.0
 */
public class BusinessException extends RuntimeException {

    private String tipsMessage;

    public BusinessException(String tipsMessage) {
        this.tipsMessage = tipsMessage;
    }

    public String getTipsMessage() {
        return tipsMessage;
    }

    public void setTipsMessage(String tipsMessage) {
        this.tipsMessage = tipsMessage;
    }
}
