package com.fairyland.xrobot.modular.xrobot.domain.req;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->TaskExeResultReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-17 20:24
 **/
public class TaskExeResultReq extends PageRequest {

    private Logger logger = LoggerFactory.getLogger(TaskExeResultReq.class);

    private String taskid;

    private String deviceid;

    private String currentUser;

    private String taskclass;

    public String getTaskclass() {
        return taskclass;
    }

    public void setTaskclass(String taskclass) {
        this.taskclass = taskclass;
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

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }


    @Override
    public String toString() {
        return "TaskExeResultReq{" +
                "taskid='" + taskid + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", currentUser='" + currentUser + '\'' +
                ", taskclass='" + taskclass + '\'' +
                '}';
    }
}
