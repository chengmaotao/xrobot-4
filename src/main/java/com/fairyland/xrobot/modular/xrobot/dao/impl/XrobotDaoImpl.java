package com.fairyland.xrobot.modular.xrobot.dao.impl;

import com.fairyland.xrobot.common.constant.XRobotCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClinetLoginReq;
import com.fairyland.xrobot.modular.xrobot.dao.XrobotDao;
import com.fairyland.xrobot.modular.xrobot.dao.mapper.*;
import com.fairyland.xrobot.modular.xrobot.domain.*;
import com.fairyland.xrobot.modular.xrobot.domain.req.*;
import com.fairyland.xrobot.modular.xrobot.domain.resp.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @program: fairyland->XrobotDaoImpl
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-22 18:32
 **/
@Repository
public class XrobotDaoImpl implements XrobotDao {


    @Autowired
    private DeviceMapper deviceMapper;


    @Autowired
    private DeviceGroupMapper deviceGroupMapper;


    @Autowired
    private DeviceGroupMembersMapper deviceGroupMembersMapper;


    @Autowired
    private DictMapper dictMapper;


    @Autowired
    private TaskDictMapper taskDictMapper;

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private TaskDevicesMapper taskDevicesMapper;

    @Autowired
    private PushJoinGroupsMapper pushJoinGroupsMapper;

    @Autowired
    private PushMessagesMapper pushMessagesMapper;

    @Autowired
    private CommentJoinGroupsMapper commentJoinGroupsMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private CreateGroupsMapper createGroupsMapper;

    @Autowired
    private SummaryJoinGroupsMapper summaryJoinGroupsMapper;


    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    public List<Device> deviceList(DeviceListReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<Device> list = deviceMapper.deviceList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public int insertDevice(Device record) {
        return deviceMapper.insertSelective(record);
    }

    @Override
    public int updateDevice(Device record, DeviceExample example) {

        return deviceMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<Device> getDeviceListByDeviceSN(String devicesn, String userName) {

        DeviceExample example = new DeviceExample();
        example.createCriteria().andDevicesnEqualTo(devicesn).andDelFlagEqualTo(XRobotCode.DEL_0).andCreateByEqualTo(userName);

        List<Device> list = deviceMapper.selectByExample(example);

        return list;
    }

    @Override
    public List<Device> getDeviceListByPhone(String phone, String userName) {
        DeviceExample example = new DeviceExample();
        example.createCriteria().andPhoneEqualTo(phone).andDelFlagEqualTo(XRobotCode.DEL_0).andCreateByEqualTo(userName);

        List<Device> list = deviceMapper.selectByExample(example);

        return list;
    }


    @Override
    public void resetDeviceState(DeviceExample example, Device record) {

        deviceMapper.updateByExampleSelective(record, example);
    }

    @Override
    public Device getDeviceInfoById(Long id, String userName) {

        DeviceExample example = new DeviceExample();
        example.createCriteria().andIdEqualTo(id).andCreateByEqualTo(userName).andDelFlagEqualTo(XRobotCode.DEL_0);

        List<Device> list = deviceMapper.selectByExample(example);

        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }

    @Override
    public List<DeviceGroup> deviceGroupList(DeviceGroupListReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<DeviceGroup> list = deviceGroupMapper.deviceGroupList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public DeviceGroup getDeviceGroupInfoById(Long id, String userName) {

        DeviceGroupExample example = new DeviceGroupExample();
        example.createCriteria().andIdEqualTo(id).andCreateByEqualTo(userName).andDelFlagEqualTo(XRobotCode.DEL_0);

        List<DeviceGroup> list = deviceGroupMapper.selectByExample(example);

        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<DeviceGroup> getDeviceGroupListByName(String groupname, String userName) {

        DeviceGroupExample example = new DeviceGroupExample();
        example.createCriteria().andGroupnameEqualTo(groupname).andDelFlagEqualTo(XRobotCode.DEL_0).andCreateByEqualTo(userName);

        return deviceGroupMapper.selectByExample(example);
    }

    @Override
    public int insertDeviceGroup(DeviceGroup record) {
        return deviceGroupMapper.insertSelective(record);
    }

    @Override
    public int updateDeviceGroup(DeviceGroup record, DeviceGroupExample example) {

        return deviceGroupMapper.updateByExampleSelective(record, example);
    }

    @Override
    public void delDeviceGroup(DeviceGroup record, DeviceGroupExample example) {

        deviceGroupMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<Device> deviceAllList(String userName, Integer role) {

        DeviceExample example = new DeviceExample();

        if (role == null) {
            example.createCriteria().andDelFlagEqualTo(XRobotCode.DEL_0).andCreateByEqualTo(userName);
        } else {
            example.createCriteria().andDelFlagEqualTo(XRobotCode.DEL_0).andCreateByEqualTo(userName).andRoleEqualTo(role);
        }


        example.setOrderByClause(" create_date desc");
        List<Device> list = deviceMapper.selectByExample(example);

        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<DeviceGroup> deviceGroupAllList(String userName) {
        DeviceGroupExample example = new DeviceGroupExample();
        example.createCriteria().andDelFlagEqualTo(XRobotCode.DEL_0).andCreateByEqualTo(userName);
        example.setOrderByClause(" create_date desc");
        List<DeviceGroup> list = deviceGroupMapper.selectByExample(example);

        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<DeviceGroupMembersListResp> deviceGroupMembersList(DeviceGroupMembersListReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<DeviceGroupMembersListResp> list = deviceGroupMembersMapper.deviceGroupMembersList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public void delDeviceGroupMembers(Long id, String userName) {

        DeviceGroupMembersExample example = new DeviceGroupMembersExample();
        example.createCriteria().andIdEqualTo(id).andCreateByEqualTo(userName);

        deviceGroupMembersMapper.deleteByExample(example);
    }

    @Override
    public List<DeviceGroupMembersInitResp> saveDeviceGroupMembersInit(DeviceGroupMembersInitReq paramReq) {

        return deviceGroupMembersMapper.saveDeviceGroupMembersInit(paramReq);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, value = "phoenixTransactionManager")
    public void saveDeviceGroupMembers(DeviceGroupMembersReq paramReq) {

        DeviceGroupMembersExample delexample = new DeviceGroupMembersExample();
        delexample.createCriteria().andGroupidEqualTo(paramReq.getGroupid()).andCreateByEqualTo(paramReq.getUserName());

        deviceGroupMembersMapper.deleteByExample(delexample);

        List<String> deviceids = paramReq.getDeviceidList();

        if (deviceids == null || deviceids.isEmpty()) {
            return;
        }

        Map<String, Object> params = new HashMap<>();

        params.put("groupid", paramReq.getGroupid());
        params.put("list", deviceids);
        params.put("nowDate", new Date());
        params.put("userName", paramReq.getUserName());

        deviceGroupMembersMapper.batchInsertData(params);

    }

    @Override
    public QRCodeResp getQRCodeJsonById(DelDeviceReq paramReq) {

        return deviceMapper.getQRCodeJsonById(paramReq);
    }

    @Override
    public Device checkClinetLogin(ClinetLoginReq paramReq) {

        DeviceExample example = new DeviceExample();
        if (StringUtils.isNotEmpty(paramReq.getAccount()) && StringUtils.isNotEmpty(paramReq.getAccount1())) {
            example.createCriteria().andDeviceidEqualTo(paramReq.getId()).andTokenEqualTo(paramReq.getToken()).andAccountEqualTo(paramReq.getAccount()).andAccount1EqualTo(paramReq.getAccount1()).andPhoneEqualTo(paramReq.getPhone()).andDelFlagEqualTo("0");
        } else if (StringUtils.isNotEmpty(paramReq.getAccount())) {
            example.createCriteria().andDeviceidEqualTo(paramReq.getId()).andTokenEqualTo(paramReq.getToken()).andAccountEqualTo(paramReq.getAccount()).andPhoneEqualTo(paramReq.getPhone()).andDelFlagEqualTo("0");
        } else if (StringUtils.isNotEmpty(paramReq.getAccount1())) {
            example.createCriteria().andDeviceidEqualTo(paramReq.getId()).andTokenEqualTo(paramReq.getToken()).andAccount1EqualTo(paramReq.getAccount1()).andPhoneEqualTo(paramReq.getPhone()).andDelFlagEqualTo("0");
        } else {
            example.createCriteria().andDeviceidEqualTo(paramReq.getId()).andTokenEqualTo(paramReq.getToken()).andPhoneEqualTo(paramReq.getPhone()).andDelFlagEqualTo("0");
        }

        List<Device> list = deviceMapper.selectByExample(example);

        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Dict> dictList() {
        DictExample example = new DictExample();
        example.createCriteria().andDelFlagEqualTo(XRobotCode.DEL_0);
        example.setOrderByClause(" create_date desc");
        List<Dict> list = dictMapper.selectByExample(example);

        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public void saveDict(Dict record) {
        dictMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<TaskDict> taskDictList() {

        TaskDictExample example = new TaskDictExample();
        example.createCriteria().andDelFlagEqualTo(XRobotCode.DEL_0);
        example.setOrderByClause(" create_date desc");

        return taskDictMapper.selectByExample(example);
    }

    @Override
    public List<TasksWithBLOBs> taskList(TaskListReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<TasksWithBLOBs> list = tasksMapper.taskList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public TasksWithBLOBs getTaskInfoById(String taskId, String userName) {
        TasksExample example = new TasksExample();
        example.createCriteria().andTaskidEqualTo(taskId).andCreateByEqualTo(userName).andDelFlagEqualTo(XRobotCode.DEL_0);

        List<TasksWithBLOBs> list = tasksMapper.selectByExampleWithBLOBs(example);

        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }

    @Override
    public int insertTasks(TasksWithBLOBs record) {
        return tasksMapper.insertSelective(record);
    }

    @Override
    public int updateTasks(TasksWithBLOBs record) {
        //return tasksMapper.updateByPrimaryKeyWithBLOBs(record);
        return tasksMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void insertTasksDevices(Map<String, Object> dbParams) {
        taskDevicesMapper.insertTasksDevices(dbParams);
    }

    @Override
    public void delTasksDevicesByTaskId(String taskid, String userName) {

        TaskDevicesExample example = new TaskDevicesExample();
        example.createCriteria().andCreateByEqualTo(userName).andTaskidEqualTo(taskid);

        taskDevicesMapper.deleteByExample(example);
    }

    @Override
    public int exeTask(Tasks record) {
        return tasksMapper.exeTask(record);
    }

    @Override
    public List<TaskDevicesResp> taskDevicesList(TaskDevicesListReq paramReq) {

        //TaskDevicesExample example = new TaskDevicesExample();
        //example.createCriteria().andDelFlagEqualTo(XRobotCode.DEL_0).andCreateByEqualTo(paramReq.getCurrentUser()).andTaskidEqualTo(paramReq.getTaskid());
        List<TaskDevicesResp> list = taskDevicesMapper.selectTaskDevicesList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public List<DeviceGroupMembersListResp> deviceGroupMembersAllList(DeviceGroupMembersListReq paramReq) {

        List<DeviceGroupMembersListResp> list = deviceGroupMembersMapper.deviceGroupMembersList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public List<SaveTaskInitResp> saveTaskInit(SaveTaskInitReq paramReq) {
        return tasksMapper.saveTaskInit(paramReq);
    }

    @Override
    public List<TaskDevices> taskDevicesAllList(String taskid, String userName) {
        TaskDevicesExample example = new TaskDevicesExample();
        example.createCriteria().andDelFlagEqualTo(XRobotCode.DEL_0).andCreateByEqualTo(userName).andTaskidEqualTo(taskid);
        List<TaskDevices> list = taskDevicesMapper.selectByExample(example);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public List<PushJoinGroups> pushJoinGroupsList(ExeResultReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);


        List<PushJoinGroups> list = pushJoinGroupsMapper.pushJoinGroupsList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public List<PushMessages> pushMessagesList(ExeResultReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);


        List<PushMessages> list = pushMessagesMapper.pushMessagesList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public List<CommentJoinGroups> commentJoinGroupsList(ExeResultReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);


        List<CommentJoinGroups> list = commentJoinGroupsMapper.commentJoinGroupsList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public List<Comments> commentsList(ExeResultReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);


        List<Comments> list = commentsMapper.commentsList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public List<CreateGroups> createGroupsList(ExeResultReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);


        List<CreateGroups> list = createGroupsMapper.createGroupsList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public List<Device> deviceAllListByUser(DeviceListReq paramReq) {

        List<Device> list = deviceMapper.deviceList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public List<SummaryJoinGroups> summaryJoinGroupsList(ExeResultReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);


        List<SummaryJoinGroups> list = summaryJoinGroupsMapper.summaryJoinGroupsList(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public void deleteAppVersion() {
        appVersionMapper.deleteByExample(null);
    }

    @Override
    public void insertAppVersion(AppVersion record) {
        appVersionMapper.insertSelective(record);
    }

    @Override
    public AppVersion getAppVersion() {

        AppVersionExample example = new AppVersionExample();
        example.setOrderByClause(" create_date desc");

        List<AppVersion> list = appVersionMapper.selectByExample(example);

        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void updateTaskDevices(TaskDevices taskDevicesRecord, TaskDevicesExample example) {
        taskDevicesMapper.updateByExampleSelective(taskDevicesRecord, example);
    }

    @Override
    public List<TaskExeResultResp> taskExeResult(TaskExeResultReq paramReq) {
        int pageNum = paramReq.getPageNum();
        int pageSize = paramReq.getPageSize();
        PageHelper.startPage(pageNum, pageSize);


        List<TaskExeResultResp> list = taskDevicesMapper.taskExeResult(paramReq);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public Device getDeviceInfoByDeviceId(String userName, String deviceid) {
        DeviceExample example = new DeviceExample();
        example.createCriteria().andDeviceidEqualTo(deviceid).andCreateByEqualTo(userName);

        List<Device> list = deviceMapper.selectByExample(example);

        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
