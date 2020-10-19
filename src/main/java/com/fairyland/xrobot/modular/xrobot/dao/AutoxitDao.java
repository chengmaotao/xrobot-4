package com.fairyland.xrobot.modular.xrobot.dao;

import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClientCheckPushMessageReq;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClientSubmitPushJoinGroupsReq;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ServerTaskNotifyCommandReq;
import com.fairyland.xrobot.modular.xrobot.domain.*;

import java.util.List;
import java.util.Map;

public interface AutoxitDao {
    Device getDeviceBydeviceID(String id);

    void updateDeviceByid(Device record);

    List<ServerTaskNotifyCommandReq> getClientGetTaskByDeviceId(String deviceid);

    PushMessages clientCheckPushMessageStatus(ClientCheckPushMessageReq paramReq);

    TasksWithBLOBs getTaskByTaskId(String taskId,String taskClass);

    void insertSummaryJoinGroups(SummaryJoinGroups record);

    void insertPushJoinGroups(Map<String,Object> dbParams);

    void insertPushMessages(PushMessages record);

    void insertCommentJoinGroups(Map<String, Object> dbParams);

    void insertComments(Comments record);

    void insertCreateGroups(CreateGroups record);

    void updateTaskDevices(TaskDevices record, TaskDevicesExample example);

    List<TaskDevices> getTaskDevicesIsNotComplete(String taskID);

    void updateTask(TasksWithBLOBs tasksRecord, TasksExample tasksExample);

    void insertWsusernumber(Wsusernumbers record);
}
