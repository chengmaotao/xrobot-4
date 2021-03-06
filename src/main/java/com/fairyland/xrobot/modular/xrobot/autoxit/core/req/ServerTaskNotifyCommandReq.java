package com.fairyland.xrobot.modular.xrobot.autoxit.core.req;

import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.init.ConfigUtils;

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


    private Integer batch; // 执行批次号
    private String md5;
    private String cover; // 封面链接图片
    private String user;

    private String answers; // 加群问题答案 回车换行分割

    private String action; // 为1时表示只获取号码 不发消息


    private Integer delay;
    private Integer deadline;

    private String groupname;

    private Integer nolinks;


    private Integer maxposts; // 最大帖子数量

    public Integer getMaxposts() {
        return maxposts;
    }

    public void setMaxposts(Integer maxposts) {
        this.maxposts = maxposts;
    }

    public Integer getNolinks() {
        return nolinks;
    }

    public void setNolinks(Integer nolinks) {
        this.nolinks = nolinks;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getCover() {

        if (StringUtils.isNotEmpty(cover)) {

            //return "http://39.99.233.24:20001" + cover;


            //return "http://148.66.129.158:20001" + cover;

            return ConfigUtils.hostAddress + cover;
        }
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

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

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "ServerTaskNotifyCommandReq{" +
                "deviceid='" + deviceid + '\'' +
                ", taskid='" + taskid + '\'' +
                ", taskclass='" + taskclass + '\'' +
                ", keywords='" + keywords + '\'' +
                ", content='" + content + '\'' +
                ", batch=" + batch +
                ", md5='" + md5 + '\'' +
                ", cover='" + cover + '\'' +
                ", user='" + user + '\'' +
                ", answers='" + answers + '\'' +
                '}';
    }
}
