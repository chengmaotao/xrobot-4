package com.fairyland.xrobot.modular.xrobot.domain.resp;

import com.fairyland.xrobot.modular.xrobot.domain.TaskDevices;

/**
 * @program: fairyland->TaskDevicesResp
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-07 12:52
 **/
public class TaskDevicesResp extends TaskDevices {
    private String devicesn;
    private String phone;
    private String account;
    private String account1;

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


    private String groupname;

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public String toString() {
        return "TaskDevicesResp{" +
                "devicesn='" + devicesn + '\'' +
                ", phone='" + phone + '\'' +
                ", account='" + account + '\'' +
                ", account1='" + account1 + '\'' +
                ", groupname='" + groupname + '\'' +
                '}';
    }
}
