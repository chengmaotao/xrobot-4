package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->DeviceGroupMembersInitReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 18:00
 **/
public class DeviceGroupMembersInitReq {

    private Logger logger = LoggerFactory.getLogger(DeviceGroupMembersInitReq.class);

    private String groupid;  // 分组id

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void validate() {

        if (StringUtils.isEmpty(groupid)) {
            logger.warn("DeviceGroupMembersInitReq 分组Id  groupid = {} 不正确", groupid);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public String toString() {
        return "DeviceGroupMembersInitReq{" +
                "groupid='" + groupid + '\'' +
                '}';
    }
}
