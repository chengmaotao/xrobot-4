package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->SaveDeviceGroupReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 16:39
 **/
public class SaveDeviceGroupReq {

    private Logger logger = LoggerFactory.getLogger(SaveDeviceReq.class);

    private Long id; // 为空时新增 不为空是修改

    private String groupname; // 分组名称

    private String remarks; // 备注

    public void validate() {

        if (StringUtils.isEmpty(groupname) || groupname.length() > 64) {
            logger.warn("SaveDeviceReq 终端设备应用分组 groupname = {} 不正确 超出长度限制64", groupname);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (StringUtils.isNotEmpty(remarks) && remarks.length() > 255) {
            logger.warn("SaveDeviceReq 终端设备应用分组 备注 remarks = {} 不正确 超出长度限制255", remarks);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public String toString() {
        return "SaveDeviceGroupReq{" +
                "id=" + id +
                ", groupname='" + groupname + '\'' +
                '}';
    }
}
