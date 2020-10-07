package com.fairyland.xrobot.modular.xrobot.domain.resp;

/**
 * @program: fairyland->DeviceGroupMembersListResp
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 17:20
 **/
public class DeviceGroupMembersListResp {

    private Long id;  // 唯一标识

    private String groupid;  // 分组id

    private String groupname; // 分组名称

    private String devicesn; // 终端设备应用编号

    private String phone; // 终端设备手机号码

    private String account; // 目标APP账号(facebook)

    private Integer login; // 登录方式(0:账号密码 1:手机号短信验证码)

    private String account1; // 目标APP1账号(wsup)

    private Integer login1; // 登录方式(0:账号密码 1:手机号短信验证码)

    private Integer state; // 0:未连接 1:正常（连接并登录成功）98:账号禁用 99:账号1禁用 100:账号全部禁用 127:暂停使用


    private String deviceid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
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

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getAccount1() {
        return account1;
    }

    public void setAccount1(String account1) {
        this.account1 = account1;
    }

    public Integer getLogin1() {
        return login1;
    }

    public void setLogin1(Integer login1) {
        this.login1 = login1;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    @Override
    public String toString() {
        return "DeviceGroupMembersListResp{" +
                "id=" + id +
                ", groupid='" + groupid + '\'' +
                ", groupname='" + groupname + '\'' +
                ", devicesn='" + devicesn + '\'' +
                ", phone='" + phone + '\'' +
                ", account='" + account + '\'' +
                ", login=" + login +
                ", account1='" + account1 + '\'' +
                ", login1=" + login1 +
                ", state=" + state +
                ", deviceid='" + deviceid + '\'' +
                '}';
    }
}
