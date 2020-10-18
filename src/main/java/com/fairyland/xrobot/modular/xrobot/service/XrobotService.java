package com.fairyland.xrobot.modular.xrobot.service;

import com.fairyland.xrobot.modular.xrobot.domain.*;
import com.fairyland.xrobot.modular.xrobot.domain.req.*;
import com.fairyland.xrobot.modular.xrobot.domain.resp.*;

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

    List<Device> deviceAllList(Integer role);

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

    List<TaskDevicesResp> taskDevicesList(TaskDevicesListReq paramReq);

    Map<String, Object> saveTaskInit(SaveTaskInitReq paramReq);

    List<DeviceGroupMembersListResp> deviceGroupMembersAllList(DeviceGroupMembersListReq paramReq);

    List<Device> monitorDeviceList(DeviceListReq paramReq);

    void serverStart(DelDeviceReq paramReq);

    void serverExit(DelDeviceReq paramReq);

    void serverQuiet(DelDeviceReq paramReq);

    PageResult taskResultList(ExeResultReq paramReq);

    void deviceResetAllowState(DeviceResetAllowStateReq paramReq);

    List<Device> monitorAdminDeviceList(DeviceListReq paramReq);

    void appUpload(AppUploadReq paramReq, HttpServletRequest request);

    AppVersion getAppUrl();

    PageResult taskExeResultList(TaskExeResultReq paramReq);

    String exportUserNumberList();
}
