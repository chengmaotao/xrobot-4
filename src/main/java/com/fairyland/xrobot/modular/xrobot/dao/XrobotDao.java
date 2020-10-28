package com.fairyland.xrobot.modular.xrobot.dao;

import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClinetLoginReq;
import com.fairyland.xrobot.modular.xrobot.domain.*;
import com.fairyland.xrobot.modular.xrobot.domain.req.*;
import com.fairyland.xrobot.modular.xrobot.domain.resp.*;

import java.util.List;
import java.util.Map;

public interface XrobotDao {

    List<Device> deviceList(DeviceListReq paramReq);

    int insertDevice(Device record);

    int updateDevice(Device record,DeviceExample example);

    List<Device> getDeviceListByDeviceSN(String devicesn,String userName);

    List<Device> getDeviceListByPhone(String phone,String userName);

    void resetDeviceState(DeviceExample example, Device record);

    Device getDeviceInfoById(Long id,String userName);

    List<DeviceGroup> deviceGroupList(DeviceGroupListReq paramReq);

    DeviceGroup getDeviceGroupInfoById(Long id,String userName);

    List<DeviceGroup> getDeviceGroupListByName(String groupname,String userName);

    int insertDeviceGroup(DeviceGroup record);

    int updateDeviceGroup(DeviceGroup record,DeviceGroupExample example);

    void delDeviceGroup(DeviceGroup record, DeviceGroupExample example);

    List<Device> deviceAllList(String userName,Integer role);

    List<DeviceGroup> deviceGroupAllList(String userName);

    List<DeviceGroupMembersListResp> deviceGroupMembersList(DeviceGroupMembersListReq paramReq);

    void delDeviceGroupMembers(Long id,String userName);

    List<DeviceGroupMembersInitResp> saveDeviceGroupMembersInit(DeviceGroupMembersInitReq paramReq);

    void saveDeviceGroupMembers(DeviceGroupMembersReq paramReq);

    QRCodeResp getQRCodeJsonById(DelDeviceReq paramReq);

    Device checkClinetLogin(ClinetLoginReq paramReq);

    List<Dict> dictList();

    void saveDict(Dict record);

    List<TaskDict> taskDictList();

    List<TasksWithBLOBs> taskList(TaskListReq paramReq);

    TasksWithBLOBs getTaskInfoById(String taskId, String userName);

    int insertTasks(TasksWithBLOBs record);

    int updateTasks(TasksWithBLOBs record);

    void insertTasksDevices(Map<String, Object> dbParams);

    void delTasksDevicesByTaskId(String taskid, String userName);

    int exeTask(Tasks record);

    List<TaskDevicesResp> taskDevicesList(TaskDevicesListReq paramReq);

    List<DeviceGroupMembersListResp> deviceGroupMembersAllList(DeviceGroupMembersListReq paramReq);

    List<SaveTaskInitResp> saveTaskInit(SaveTaskInitReq paramReq);

    List<TaskDevices> taskDevicesAllList(String taskid, String userName);

    List<PushJoinGroups> pushJoinGroupsList(ExeResultReq paramReq);

    List<PushMessages> pushMessagesList(ExeResultReq paramReq);

    List<CommentJoinGroups> commentJoinGroupsList(ExeResultReq paramReq);

    List<Comments> commentsList(ExeResultReq paramReq);

    List<CreateGroups> createGroupsList(ExeResultReq paramReq);

    List<Device> deviceAllListByUser(DeviceListReq paramReq);

    List<SummaryJoinGroups> summaryJoinGroupsList(ExeResultReq paramReq);

    void deleteAppVersion();

    void insertAppVersion(AppVersion record);

    AppVersion getAppVersion();

    void updateTaskDevices(TaskDevices taskDevicesRecord, TaskDevicesExample example);

    List<TaskExeResultResp> taskExeResult(TaskExeResultReq paramReq);

    Device getDeviceInfoByDeviceId(String userName, String deviceid);

    void exeTaskDevices(TaskDevices taskDevicesRecord, TaskDevicesExample example);

    void insertTasksDevicesLog(Map<String, Object> dbParams);

    List<ExportUserNumberListResp> exportUserNumberList();

    void updateTaskDevicesLog(TaskDevicesLog taskDevicesLog, TaskDevicesLogExample taskDevicesLogExample);

    List<Wsusernumbers> userNumberList(UserNumberListReq paramReq);

    Integer getAllUserNumberCount();

    Integer userNumberCount();

    void userNumberClear();

    List<Groupnameinfo> groupnameList(GroupnameListReq paramReq);

    void delGroupnameList(GroupnameListReq paramReq);

    List<ExportDetailUserNumberListResp> exportDetailUserNumberList();
}
