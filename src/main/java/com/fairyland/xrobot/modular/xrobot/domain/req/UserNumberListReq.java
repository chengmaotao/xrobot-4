package com.fairyland.xrobot.modular.xrobot.domain.req;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @program: fairyland->UserNumberListReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-20 22:06
 **/
public class UserNumberListReq extends PageRequest implements Serializable {

    private Logger logger = LoggerFactory.getLogger(UserNumberListReq.class);

    private String taskid; // 任务唯一ID

    private Integer batch;

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

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }
}
