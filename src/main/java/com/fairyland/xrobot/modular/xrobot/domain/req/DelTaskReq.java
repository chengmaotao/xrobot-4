package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->DelDeviceReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 15:03
 **/
public class DelTaskReq {

    private Logger logger = LoggerFactory.getLogger(DelTaskReq.class);

    private String taskid; //

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void validate() {

        if (StringUtils.isEmpty(taskid)) {
            logger.warn("DelTaskReq 任务表 taskid = null 不正确");
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    @Override
    public String toString() {
        return "DelTaskReq{" +
                "taskid='" + taskid + '\'' +
                ", currentUser='" + currentUser + '\'' +
                '}';
    }
}
