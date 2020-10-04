package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->ExeTaskReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-04 19:47
 **/
public class ExeTaskReq {

    private Logger logger = LoggerFactory.getLogger(ExeTaskReq.class);

    private String taskid; // 任务唯一标识


    private String userName;

    public void validate() {

        if (StringUtils.isEmpty(taskid)) {
            logger.warn("ExeTaskReq 任务唯一标识 taskid = {} 不正确", taskid);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "ExeTaskReq{" +
                "taskid='" + taskid + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
