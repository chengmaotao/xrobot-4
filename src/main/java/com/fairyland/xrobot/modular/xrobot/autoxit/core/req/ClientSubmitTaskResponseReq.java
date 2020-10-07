package com.fairyland.xrobot.modular.xrobot.autoxit.core.req;

/**
 * @program: fairyland->ClientSubmitTaskResponseReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-07 11:49
 **/
public class ClientSubmitTaskResponseReq extends BaseClientSubmitReq {


    private Integer error;

    private String errorMsg;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ClientSubmitTaskResponseReq{" +
                "error=" + error +
                ", errorMsg='" + errorMsg + '\'' +
                "super.toString()=" + super.toString() +
                '}';
    }
}
