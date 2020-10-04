package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->SaveTaskReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-04 17:58
 **/
public class SaveTaskReq {

    private Logger logger = LoggerFactory.getLogger(SaveTaskReq.class);

    private Long id; // 为空时新增 不为空是修改

    /*    100001	搜索加群消息任务
    100002	首页链接消息任务
    100003	创建群组发帖任务
    100004	搜索加群评论任务
    100005	首页帖子评论任务*/
    private String taskclass; // 任务分类编号

    private String keywords; // 搜索关键字或待创建群名称	支持多个，回车换行分割

    private String content; // 消息、帖子、评论内容

    private String remarks; // 备注

    public void validate() {

        if (StringUtils.isNotEmpty(taskclass)
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

        if (StringUtils.isEmpty(content)) {
            logger.warn("SaveTaskReq 任务表  消息、帖子、评论内容 content = {} 不正确", content);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }


        if (StringUtils.isNotEmpty(remarks) && remarks.length() > 255) {
            logger.warn("SaveDeviceReq 任务表 备注 remarks = {} 不正确 超出长度限制255", remarks);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", taskclass='" + taskclass + '\'' +
                ", keywords='" + keywords + '\'' +
                ", content='" + content + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
