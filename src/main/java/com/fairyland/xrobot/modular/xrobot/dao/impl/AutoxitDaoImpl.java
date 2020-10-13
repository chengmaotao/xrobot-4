package com.fairyland.xrobot.modular.xrobot.dao.impl;

import com.fairyland.xrobot.common.constant.XRobotCode;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClientCheckPushMessageReq;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ServerTaskNotifyCommandReq;
import com.fairyland.xrobot.modular.xrobot.dao.AutoxitDao;
import com.fairyland.xrobot.modular.xrobot.dao.mapper.*;
import com.fairyland.xrobot.modular.xrobot.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: fairyland->AutoxitDaoImpl
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-05 20:53
 **/
@Repository
public class AutoxitDaoImpl implements AutoxitDao {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private TaskDevicesMapper taskDevicesMapper;


    @Autowired
    private PushMessagesMapper pushMessagesMapper;

    @Autowired
    private TasksMapper tasksMapper;


    @Autowired
    private SummaryJoinGroupsMapper summaryJoinGroupsMapper;


    @Autowired
    private PushJoinGroupsMapper pushJoinGroupsMapper;


    @Autowired
    private CommentJoinGroupsMapper commentJoinGroupsMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private CreateGroupsMapper createGroupsMapper;

    @Override
    public Device getDeviceBydeviceID(String deviceId) {

        DeviceExample example = new DeviceExample();
        example.createCriteria().andDeviceidEqualTo(deviceId).andDelFlagEqualTo(XRobotCode.DEL_0);
        List<Device> list = deviceMapper.selectByExample(example);

        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void updateDeviceByid(Device record) {
        deviceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<ServerTaskNotifyCommandReq> getClientGetTaskByDeviceId(String deviceid) {
        List<ServerTaskNotifyCommandReq> list = taskDevicesMapper.getClientGetTaskByDeviceId(deviceid);

        if (list == null) {
            list = new ArrayList();
        }
        return list;
    }

    @Override
    public PushMessages clientCheckPushMessageStatus(ClientCheckPushMessageReq paramReq) {

        List<PushMessages> list = pushMessagesMapper.clientCheckPushMessageStatus(paramReq);

        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public TasksWithBLOBs getTaskByTaskId(String taskId, String taskCalss) {

        TasksExample example = new TasksExample();
        example.createCriteria().andTaskidEqualTo(taskId).andTaskclassEqualTo(taskCalss);

        List<TasksWithBLOBs> list = tasksMapper.selectByExampleWithBLOBs(example);

        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void insertSummaryJoinGroups(SummaryJoinGroups record) {

        tasksMapper.setNamesUtf8mb4();

        summaryJoinGroupsMapper.insertSelective(record);
    }

    @Override
    public void insertPushJoinGroups(Map<String, Object> dbParams) {

        tasksMapper.setNamesUtf8mb4();
        pushJoinGroupsMapper.insertPushJoinGroups(dbParams);
    }

    @Override
    public void insertPushMessages(PushMessages record) {
        tasksMapper.setNamesUtf8mb4();
        pushMessagesMapper.insertSelective(record);
    }

    @Override
    public void insertCommentJoinGroups(Map<String, Object> dbParams)
    {
        tasksMapper.setNamesUtf8mb4();
        commentJoinGroupsMapper.insertCommentJoinGroups(dbParams);
    }

    @Override
    public void insertComments(Comments record) {
        tasksMapper.setNamesUtf8mb4();
        commentsMapper.insertSelective(record);
    }

    @Override
    public void insertCreateGroups(CreateGroups record) {
        tasksMapper.setNamesUtf8mb4();
        createGroupsMapper.insertSelective(record);
    }

    @Override
    public void updateTaskDevices(TaskDevices record, TaskDevicesExample example) {

        taskDevicesMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<TaskDevices> getTaskDevicesIsNotComplete(String taskID) {

        TaskDevicesExample example = new TaskDevicesExample();
        example.createCriteria().andTaskidEqualTo(taskID).andStateNotEqualTo(2);
        return taskDevicesMapper.selectByExample(example);
    }

    @Override
    public void updateTask(TasksWithBLOBs tasksRecord, TasksExample tasksExample) {
        tasksMapper.updateByExampleSelective(tasksRecord, tasksExample);
    }
}
