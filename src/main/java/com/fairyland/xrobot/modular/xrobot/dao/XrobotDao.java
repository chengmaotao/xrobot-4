package com.fairyland.xrobot.modular.xrobot.dao;

import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClinetLoginReq;
import com.fairyland.xrobot.modular.xrobot.domain.Device;
import com.fairyland.xrobot.modular.xrobot.domain.DeviceExample;
import com.fairyland.xrobot.modular.xrobot.domain.DeviceGroup;
import com.fairyland.xrobot.modular.xrobot.domain.req.DeviceGroupListReq;
import com.fairyland.xrobot.modular.xrobot.domain.req.DeviceGroupMembersListReq;
import com.fairyland.xrobot.modular.xrobot.domain.req.DeviceGroupMembersReq;
import com.fairyland.xrobot.modular.xrobot.domain.req.DeviceListReq;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersInitResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersListResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.QRCodeResp;

import java.util.List;

public interface XrobotDao {

    List<Device> deviceList(DeviceListReq paramReq);

    int insertDevice(Device record);

    int updateDevice(Device record);

    List<Device> getDeviceListByDeviceSN(String devicesn);

    List<Device> getDeviceListByPhone(String phone);

    void delDevice(Device record);

    void resetDeviceState(DeviceExample example, Device record);

    Device getDeviceInfoById(Long id);

    List<DeviceGroup> deviceGroupList(DeviceGroupListReq paramReq);

    DeviceGroup getDeviceGroupInfoById(Long id);

    List<DeviceGroup> getDeviceGroupListByName(String groupname);

    int insertDeviceGroup(DeviceGroup record);

    int updateDeviceGroup(DeviceGroup record);

    void delDeviceGroup(DeviceGroup record);

    List<Device> deviceAllList();

    List<DeviceGroup> deviceGroupAllList();

    List<DeviceGroupMembersListResp> deviceGroupMembersList(DeviceGroupMembersListReq paramReq);

    void delDeviceGroupMembers(Long id);

    List<DeviceGroupMembersInitResp> saveDeviceGroupMembersInit(String groupid);

    void saveDeviceGroupMembers(DeviceGroupMembersReq paramReq);

    QRCodeResp getQRCodeJsonById(Long id);

    Device checkClinetLogin(ClinetLoginReq paramReq);
}
