package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @program: fairyland->DeviceGroupMembersReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 18:27
 **/
public class DeviceGroupMembersReq {

    private Logger logger = LoggerFactory.getLogger(DeviceGroupMembersReq.class);

    private String groupid;   // 分组唯一ID

    private String deviceids;  // 终端设备记录唯一ID 集合

    private List<String> deviceidList;  // 终端设备记录唯一ID 集合


    private String userName;

    public void validate() {

        if (StringUtils.isEmpty(groupid)) {
            logger.warn("DeviceGroupMembersReq 分组唯一ID groupid = {} 不正确", groupid);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (StringUtils.isNotEmpty(deviceids)) {
            String[] split = deviceids.split(",");

            List<String> tempDeviceidList = Arrays.asList(split);
            setDeviceidList(tempDeviceidList);
        }

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

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "DeviceGroupMembersReq{" +
                "groupid='" + groupid + '\'' +
                ", deviceids=" + deviceids +
                ", userName='" + userName + '\'' +
                '}';
    }
}
