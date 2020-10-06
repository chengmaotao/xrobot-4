package com.fairyland.xrobot.modular.xrobot.autoxit.core.req;

/**
 * @program: fairyland->ClientCheckPushMessageReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-06 19:43
 **/
public class ClientCheckPushMessageReq {

    private String id; // 终端设备应用编号
    private String phone; //

    private String taskID; //

    private String taskclass; //



    private Integer batch; //

    private String md5; //

    private String usernumber; //


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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    @Override
    public String toString() {
        return "ClientCheckPushMessageReq{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", taskID='" + taskID + '\'' +
                ", taskclass='" + taskclass + '\'' +
                ", batch=" + batch +
                ", md5='" + md5 + '\'' +
                ", usernumber='" + usernumber + '\'' +
                '}';
    }
}
