package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @program: fairyland->SaveTaskReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-04 17:58
 **/
public class SaveTaskReq {

    private Logger logger = LoggerFactory.getLogger(SaveTaskReq.class);

    private String taskid; // 为空时新增 不为空是修改

    /*    100001	搜索加群消息任务
    100002	首页链接消息任务
    100003	创建群组发帖任务
    100004	搜索加群评论任务
    100005	首页帖子评论任务*/
    private String taskclass; // 任务分类编号

    private String keywords; // 搜索关键字或待创建群名称	支持多个，回车换行分割

    private String content; // 消息、帖子、评论内容

    private String remarks; // 备注


    private String deviceids;  // 终端设备记录唯一ID 集合  逗号隔开

    private List<String> deviceidList;  // 终端设备记录唯一ID 集合

    private String answers = ""; // 加群问题答案


    private String action = "0"; // 为1时表示只获取号码 不发消息


    private Integer delay; // 帖子全部浏览完成等待确认时间(单位秒) 默认20

    private Integer deadline; // 浏览帖子的截止日期(2018表示2018年 及之前的帖子不再处理，即只处理2019年及以后的帖子) 默认2018


    private String groupname = ""; // 目标群名称

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

    public void validate() {

        if (StringUtils.isEmpty(taskclass)
                ||
                (!StringUtils.equals("100001", taskclass)
                        && !StringUtils.equals("100002", taskclass)
                        && !StringUtils.equals("100003", taskclass)
                        && !StringUtils.equals("100004", taskclass)
                        && !StringUtils.equals("100005", taskclass))) {
            logger.warn("SaveTaskReq 任务表 任务分类编号 taskclass = {} 不正确", taskclass);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (StringUtils.equals("100001", taskclass) || StringUtils.equals("100003", taskclass) || StringUtils.equals("100004", taskclass)) {
            if (StringUtils.isEmpty(keywords)) {
                logger.warn("SaveTaskReq 任务表 搜索关键字或待创建群名称 keywords = {} 不正确", keywords);
                throw new XRobotException(ErrorCode.ERROR_CODE_5);
            }
        }

        if (delay == null) {
            delay = 20;
        }

        if (deadline == null) {
            deadline = 2018;
        }

        if (StringUtils.equals("100001", taskclass) || StringUtils.equals("100002", taskclass)) {
            if (StringUtils.isEmpty(action) || (!StringUtils.equals("0", action) && !StringUtils.equals("1", action))) {

                logger.warn("SaveTaskReq 任务是 100001 或者 100002 时 action = {} 不正确", action);
                throw new XRobotException(ErrorCode.ERROR_CODE_5);
            }

            if (StringUtils.equals("0", action)) {
                if (StringUtils.isEmpty(content)) {
                    logger.warn("SaveTaskReq 任务表  消息、帖子、评论内容 content = {} 不正确", content);
                    throw new XRobotException(ErrorCode.ERROR_CODE_5);
                }
            }

        } else {
            if (StringUtils.isEmpty(content)) {
                logger.warn("SaveTaskReq 任务表  消息、帖子、评论内容 content = {} 不正确", content);
                throw new XRobotException(ErrorCode.ERROR_CODE_5);
            }
        }

        if (StringUtils.isNotEmpty(groupname) && groupname.length() > 128) {
            logger.warn("SaveDeviceReq 任务表 备注 groupname = {} 不正确 超出长度限制128", groupname);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }


        if (StringUtils.isNotEmpty(remarks) && remarks.length() > 255) {
            logger.warn("SaveDeviceReq 任务表 备注 remarks = {} 不正确 超出长度限制255", remarks);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (StringUtils.isEmpty(deviceids)) {
            logger.warn("SaveDeviceReq  deviceids = {} 不正确", deviceids);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        String[] split = deviceids.split(",");

        List<String> tempDeviceidList = Arrays.asList(split);
        setDeviceidList(tempDeviceidList);

    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getDeviceids() {
        return deviceids;
    }

    public void setDeviceids(String deviceids) {
        this.deviceids = deviceids;
    }

    public List<String> getDeviceidList() {
        return deviceidList;
    }

    public void setDeviceidList(List<String> deviceidList) {
        this.deviceidList = deviceidList;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Override
    public String toString() {
        return "SaveTaskReq{" +
                "taskid='" + taskid + '\'' +
                ", taskclass='" + taskclass + '\'' +
                ", keywords='" + keywords + '\'' +
                ", content='" + content + '\'' +
                ", remarks='" + remarks + '\'' +
                ", deviceids='" + deviceids + '\'' +
                ", deviceidList=" + deviceidList +
                ", answers='" + answers + '\'' +
                '}';
    }
}
