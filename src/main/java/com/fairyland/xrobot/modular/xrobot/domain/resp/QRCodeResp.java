package com.fairyland.xrobot.modular.xrobot.domain.resp;

/**
 * @program: fairyland->QRCodeResp
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 19:01
 **/
public class QRCodeResp {

    private String token; // 客户端登录token

    private String phone; // 终端设备手机号码

    private String account; // 目标APP账号(facebook)
    private String password;

    private String account1; // 目标APP1账号(wsup)
    private String password1;

    private String devicesn; // 终端设备应用编号


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount1() {
        return account1;
    }

    public void setAccount1(String account1) {
        this.account1 = account1;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevicesn() {
        return devicesn;
    }

    public void setDevicesn(String devicesn) {
        this.devicesn = devicesn;
    }


    @Override
    public String toString() {
        return "QRCodeResp{" +
                "token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", account1='" + account1 + '\'' +
                ", password1='" + password1 + '\'' +
                ", devicesn='" + devicesn + '\'' +
                '}';
    }
}
