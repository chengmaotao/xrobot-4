package com.fairyland.xrobot.modular.xrobot.domain.req;

/**
 * @program: fairyland->TaskListReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-04 17:22
 **/
public class TaskDevicesListReq extends PageRequest {

    private String taskid; // 任务唯一ID


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
        return "TaskListReq{" +
                "taskid='" + taskid + '\'' +
                '}';
    }
}
