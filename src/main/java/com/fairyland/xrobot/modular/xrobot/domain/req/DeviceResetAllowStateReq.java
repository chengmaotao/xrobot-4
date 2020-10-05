package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->DeviceResetAllowStateReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-05 21:14
 **/
public class DeviceResetAllowStateReq {

    private Logger logger = LoggerFactory.getLogger(DeviceResetAllowStateReq.class);

    private Integer allow; // 0:暂停使用,1:允许使用

    private Long id; //

    public void validate() {

        if (id == null) {
            logger.warn("DeviceResetAllowStateReq 设备 id = {} 不正确", id);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if(allow == null || (allow != 0 && allow != 1)){
            logger.warn("DeviceResetAllowStateReq 设备状态 allow = {} 不正确", allow);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }
    }

    private String currentUser;

    public Integer getAllow() {
        return allow;
    }

    public void setAllow(Integer allow) {
        this.allow = allow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public String toString() {
        return "DeviceResetAllowStateReq{" +
                "allow=" + allow +
                ", id=" + id +
                ", currentUser='" + currentUser + '\'' +
                '}';
    }
}
