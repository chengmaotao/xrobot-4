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

    private String deviceid;

    private Integer login;

    private Integer login1;

    private String host;

    private Integer port;

    private Integer role;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public Integer getLogin1() {
        return login1;
    }

    public void setLogin1(Integer login1) {
        this.login1 = login1;
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


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
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
                ", deviceid='" + deviceid + '\'' +
                ", login=" + login +
                ", login1=" + login1 +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", role=" + role +
                '}';
    }
}
