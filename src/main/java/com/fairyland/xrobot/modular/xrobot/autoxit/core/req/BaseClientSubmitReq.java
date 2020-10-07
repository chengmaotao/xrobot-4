package com.fairyland.xrobot.modular.xrobot.autoxit.core.req;

/**
 * @program: fairyland->BaseClientSubmitReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-06 23:14
 **/
public class BaseClientSubmitReq {

    private String id; // 终端设备应用编号
    private String phone; //
    private String taskID; //
    private String taskclass; //
    private Integer batch; //
    private String user;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskclass() {
        return taskclass;
    }

    public void setTaskclass(String taskclass) {
        this.taskclass = taskclass;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BaseClientSubmitReq{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", taskID='" + taskID + '\'' +
                ", taskclass='" + taskclass + '\'' +
                ", batch=" + batch +
                ", user='" + user + '\'' +
                '}';
    }
}
