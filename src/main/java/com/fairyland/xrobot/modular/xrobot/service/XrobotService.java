package com.fairyland.xrobot.modular.xrobot.service;

import com.fairyland.xrobot.modular.xrobot.domain.*;
import com.fairyland.xrobot.modular.xrobot.domain.req.*;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersInitResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersListResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.PageResult;
import com.fairyland.xrobot.modular.xrobot.domain.resp.QRCodeResp;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface XrobotService {
    PageResult deviceList(DeviceListReq paramReq);

    void saveDevice(SaveDeviceReq paramReq);

    void delDevice(DelDeviceReq paramReq);

    void resetDeviceState(ResetDeviceStateReq paramReq);

    Device getDeviceInfoById(DelDeviceReq paramReq);

    PageResult deviceGroupList(DeviceGroupListReq paramReq);

    DeviceGroup getDeviceGroupInfoById(DelDeviceGroupReq paramReq);

    void saveDeviceGroup(SaveDeviceGroupReq paramReq);

    void delDeviceGroup(DelDeviceGroupReq paramReq);

    List<Device> deviceAllList();

    List<DeviceGroup> deviceGroupAllList();

    PageResult deviceGroupMembersList(DeviceGroupMembersListReq paramReq);

    void delDeviceGroupMembers(DelDeviceGroupMembersReq paramReq);

    List<DeviceGroupMembersInitResp> saveDeviceGroupMembersInit(DeviceGroupMembersInitReq paramReq);

    void saveDeviceGroupMembers(DeviceGroupMembersReq paramReq);

    QRCodeResp getQRCodeJsonById(DelDeviceReq paramReq);

    List<Dict> dictList();

    void saveDict(SaveDictReq paramReq);

    List<TaskDict> taskDictList();

    PageResult taskList(TaskListReq paramReq);

    void saveTask(SaveTaskReq paramReq, HttpServletRequest request);

    void delTask(DelTaskReq paramReq);

    void exeTask(ExeTaskReq paramReq);

    PageResult taskDevicesList(TaskDevicesListReq paramReq);

    Map<String, Object> saveTaskInit(SaveTaskInitReq paramReq);

    List<DeviceGroupMembersListResp> deviceGroupMembersAllList(DeviceGroupMembersListReq paramReq);
}
