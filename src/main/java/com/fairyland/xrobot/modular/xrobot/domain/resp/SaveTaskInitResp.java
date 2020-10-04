package com.fairyland.xrobot.modular.xrobot.domain.resp;

/**
 * @program: fairyland->SaveTaskInitResp
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-04 22:48
 **/
public class SaveTaskInitResp {

    private String deviceid;

    private String phone;

    private String tdeviceid; // 为空还未加入，不为空已经加入了

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTdeviceid() {
        return tdeviceid;
    }

    public void setTdeviceid(String tdeviceid) {
        this.tdeviceid = tdeviceid;
    }

    @Override
    public String toString() {
        return "SaveTaskInitResp{" +
                "deviceid='" + deviceid + '\'' +
                ", phone='" + phone + '\'' +
                ", tdeviceid='" + tdeviceid + '\'' +
                '}';
    }
}
