package com.fairyland.xrobot.modular.xrobot.domain.resp;

/**
 * @program: fairyland->SaveTaskInitResp
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-04 22:48
 **/
public class SaveTaskInitResp {

    private String devicesn;
    private String account;
    private String account1;

    public String getDevicesn() {
        return devicesn;
    }

    public void setDevicesn(String devicesn) {
        this.devicesn = devicesn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount1() {
        return account1;
    }

    public void setAccount1(String account1) {
        this.account1 = account1;
    }

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
                "devicesn='" + devicesn + '\'' +
                ", account='" + account + '\'' +
                ", account1='" + account1 + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", phone='" + phone + '\'' +
                ", tdeviceid='" + tdeviceid + '\'' +
                '}';
    }
}
