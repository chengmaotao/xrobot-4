package com.fairyland.xrobot.modular.xrobot.domain.resp;

import com.fairyland.xrobot.modular.xrobot.utils.Utility;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @program: fairyland->TaskExeResultResp
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-17 20:35
 **/
public class TaskExeResultResp {

    private String taskid;

    private String deviceid;

    private String errorMsg;

    private Integer state;

    private String voState;

    private String taskclass;

    private String taskclassName;

    private String deviceSN;

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date starttime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endtime;

    private Integer batch;

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getTaskclassName() {
        return Utility.getTaskClassName(getTaskclass());
    }

    public void setTaskclassName(String taskclassName) {
        this.taskclassName = taskclassName;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    // 0:新创建， 1:执行中，2:执行完成；
    public String getVoState() {

        if (state == null) {
            return "状态未知";
        } else if (state.intValue() == 0) {
            return "待执行";
        } else if (state.intValue() == 1) {
            return "执行中";
        } else if (state.intValue() == 2) {
            return "执行完成";
        }
        return "";
    }

    public void setVoState(String voState) {
        this.voState = voState;
    }

    public String getTaskclass() {
        return taskclass;
    }

    public void setTaskclass(String taskclass) {
        this.taskclass = taskclass;
    }
}
