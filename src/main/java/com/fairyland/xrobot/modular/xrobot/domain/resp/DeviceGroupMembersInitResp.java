package com.fairyland.xrobot.modular.xrobot.domain.resp;

/**
 * @program: fairyland->DeviceGroupMembersInitResp
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 18:03
 **/
public class DeviceGroupMembersInitResp {

    private String deviceid;  // 终端设备记录唯一ID （为空时 说明还未添加到该分组里。）

    private String devicesn; // 终端设备应用编号

    private String phone; // 终端设备手机号码

    private String groupid;   // 分组唯一ID


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

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public String toString() {
        return "DeviceGroupMembersInitResp{" +
                "deviceid='" + deviceid + '\'' +
                ", devicesn='" + devicesn + '\'' +
                ", phone='" + phone + '\'' +
                ", groupid='" + groupid + '\'' +
                '}';
    }
}
