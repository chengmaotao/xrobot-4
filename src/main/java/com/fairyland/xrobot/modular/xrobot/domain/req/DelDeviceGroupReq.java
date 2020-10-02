package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->DelDeviceGroupReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 16:35
 **/
public class DelDeviceGroupReq {

    private Logger logger = LoggerFactory.getLogger(DelDeviceGroupReq.class);

    private Long id; //

    public void validate() {

        if (id == null) {
            logger.warn("DelDeviceGroupReq 终端设备应用编号 id = null 不正确");
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
        return "DelDeviceGroupReq{" +
                "id=" + id +
                '}';
    }

}
