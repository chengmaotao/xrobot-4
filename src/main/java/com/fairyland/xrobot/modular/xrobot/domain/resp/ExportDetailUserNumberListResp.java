package com.fairyland.xrobot.modular.xrobot.domain.resp;

import com.fairyland.xrobot.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @program: fairyland->ExportDetailUserNumberListResp
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-28 23:37
 **/
public class ExportDetailUserNumberListResp {

    private String taskid;

    private String deviceid;

    private String devicesn;

    private String keywords;


    private String groupname;  // FB群名称

    private String groupname1; // WA群名称

    private String usernumber;


    private String createBy;

    private Date createDate;

    private String voCreateDate;


    public String getVoCreateDate() {
        if(createDate == null){
            return "";
        }
        return DateUtils.getyyyyMMddHHmmss(createDate);
    }

    public void setVoCreateDate(String voCreateDate) {
        this.voCreateDate = voCreateDate;
    }

    public String getTaskid() {
        return taskid == null ? "" : taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getDeviceid() {
        return deviceid == null ? "" : deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicesn() {
        return devicesn == null ? "" : devicesn;
    }

    public void setDevicesn(String devicesn) {
        this.devicesn = devicesn;
    }

    public String getKeywords() {
        return keywords == null ? "" : keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getGroupname() {
        return groupname == null ? "" : groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupname1() {
        return groupname1 == null ? "" : groupname1;
    }

    public void setGroupname1(String groupname1) {
        this.groupname1 = groupname1;
    }


    public String getUsernumber() {
        return usernumber == null ? "" : usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public String getCreateBy() {
        return createBy == null ? "" : createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    @Override
    public String toString() {
        return "ExportDetailUserNumberListResp{" +
                "taskid='" + taskid + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", devicesn='" + devicesn + '\'' +
                ", keywords='" + keywords + '\'' +
                ", groupname='" + groupname + '\'' +
                ", groupname1='" + groupname1 + '\'' +
                ", usernumber='" + usernumber + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
