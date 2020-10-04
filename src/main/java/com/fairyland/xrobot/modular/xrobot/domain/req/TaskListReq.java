package com.fairyland.xrobot.modular.xrobot.domain.req;

/**
 * @program: fairyland->TaskListReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-04 17:22
 **/
public class TaskListReq extends PageRequest{

    private String taskid; // 任务唯一ID

    private String taskclass; // 任务分类编号

    private String keywords; // 模糊搜索（搜索关键字或待创建群名称/消息、帖子、评论内容	）

    private Integer state; // 0:新创建， 1:执行中，2:执行完成


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

    public String getTaskclass() {
        return taskclass;
    }

    public void setTaskclass(String taskclass) {
        this.taskclass = taskclass;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TaskListReq{" +
                "taskid='" + taskid + '\'' +
                ", taskclass='" + taskclass + '\'' +
                ", keywords='" + keywords + '\'' +
                ", state=" + state +
                '}';
    }
}
