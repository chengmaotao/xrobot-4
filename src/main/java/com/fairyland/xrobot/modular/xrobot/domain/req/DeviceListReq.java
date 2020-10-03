package com.fairyland.xrobot.modular.xrobot.domain.req;

import java.io.Serializable;

/**
 * @program: fairyland->DeviceListReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 11:42
 **/
public class DeviceListReq extends PageRequest implements Serializable {


    private String devicesn; // 终端设备应用编号
    private String phone; // 终端设备手机号码

    private String account; // 目标APP账号(facebook)

    private String account1; // 目标APP1账号(wsup)

    private Integer state; // 0:未连接 1:正常（连接并登录成功）98:账号禁用 99:账号1禁用 100:账号全部禁用 127:暂停使用

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getDevicesn() {
        return devicesn;
    }

    public void setDevicesn(String devicesn) {
        this.devicesn = devicesn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DeviceListReq{" +
                "devicesn='" + devicesn + '\'' +
                ", phone='" + phone + '\'' +
                ", account='" + account + '\'' +
                ", account1='" + account1 + '\'' +
                ", state=" + state +
                '}';
    }
}
