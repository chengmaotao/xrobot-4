package com.fairyland.xrobot.modular.xrobot.domain.req;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->SaveTaskInitReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-04 22:29
 **/
public class SaveTaskInitReq {

    private Logger logger = LoggerFactory.getLogger(SaveTaskInitReq.class);

    private String taskid;

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }


    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    @Override
    public String toString() {
        return "SaveTaskInitReq{" +
                "taskid='" + taskid + '\'' +
                ", currentUser='" + currentUser + '\'' +
                '}';
    }
}
