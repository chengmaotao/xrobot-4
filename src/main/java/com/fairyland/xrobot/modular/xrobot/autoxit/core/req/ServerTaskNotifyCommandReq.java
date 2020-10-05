package com.fairyland.xrobot.modular.xrobot.autoxit.core.req;

/**
 * @program: fairyland->ServerTaskNotifyCommandReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-05 10:21
 **/
public class ServerTaskNotifyCommandReq {

    private String deviceid;// 终端设备记录唯一ID

    private String taskid; // 任务唯一ID

    /*    100001	搜索加群消息任务
        100002	首页链接消息任务
        100003	创建群组发帖任务
        100004	搜索加群评论任务
        100005	首页帖子评论任务*/
    private String taskclass; // 任务分类编号

    private String keywords; // 搜索关键字或待创建群名称; 多个，回车换行分割

    private String content; // 消息、帖子、评论内容


    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ServerTaskNotifyCommandReq{" +
                "deviceid='" + deviceid + '\'' +
                ", taskid='" + taskid + '\'' +
                ", taskclass='" + taskclass + '\'' +
                ", keywords='" + keywords + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
