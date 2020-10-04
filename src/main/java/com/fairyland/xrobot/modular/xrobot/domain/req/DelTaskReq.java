package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
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

    private Long id; //

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void validate() {

        if (id == null) {
            logger.warn("DelTaskReq 任务表 id = null 不正确");
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DelTaskReq{" +
                "id=" + id +
                '}';
    }
}
