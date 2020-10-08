package com.fairyland.xrobot.modular.xrobot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.ServerCode;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.*;
import com.fairyland.xrobot.modular.xrobot.dao.AutoxitDao;
import com.fairyland.xrobot.modular.xrobot.dao.XrobotDao;
import com.fairyland.xrobot.modular.xrobot.domain.*;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import com.fairyland.xrobot.modular.xrobot.service.AutoxitService;
import com.fairyland.xrobot.modular.xrobot.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: fairyland->AutoxitServiceImpl
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-03 09:24
 **/
@Service
public class AutoxitServiceImpl implements AutoxitService {

    private Logger logger = LoggerFactory.getLogger(AutoxitServiceImpl.class);

    @Autowired
    private XrobotDao xrobotDao;

    @Autowired
    private AutoxitDao autoxitDao;


    @Override
    public Device checkClinetLogin(ClinetLoginReq paramReq) {

        return xrobotDao.checkClinetLogin(paramReq);
    }

    /**
     * @Description: 客户端 上报 设备状态
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/5 20:47
     */
    @Override
    public void modifyDeviceStateByClientStata(String id, String status) {

/*        monitorClientAppStatus.put("0", "未就绪");
        monitorClientAppStatus.put("100", "正常");
        monitorClientAppStatus.put("200", "客户端已暂停");

        monitorClientAppStatus.put("300", "Facebook账号被禁用");
        monitorClientAppStatus.put("301", "WhatsApp账号被禁用");*/

        logger.info("modifyDeviceStateByClientStata deviceID = {},status = {}", id, status);

        Device device = autoxitDao.getDeviceBydeviceID(id);

        checkDeviceByDeviceId(device, id);

        // 0:未连接  98:账号禁用 99:账号1禁用 100:账号全部禁用

        Device record = new Device();
        record.setId(device.getId());

        logger.info("modifyDeviceStateByClientStata device state = {}", device.getState());
        if (device.getState() == 0) {

            if (StringUtils.equals("300", status)) {
                record.setState(98);
            } else if (StringUtils.equals("301", status)) {
                record.setState(99);
            }

        } else if (device.getState() == 98) {

            if (StringUtils.equals("100", status)) {
                record.setState(0);
            } else if (StringUtils.equals("301", status)) {
                record.setState(100);
            }

        } else if (device.getState() == 99) {

            if (StringUtils.equals("100", status)) {
                record.setState(0);
            } else if (StringUtils.equals("300", status)) {
                record.setState(100);
            }

        } else if (device.getState() == 100) {
            if (StringUtils.equals("100", status)) {
                record.setState(0);
            }
        }

        if (record.getState() != null) {
            autoxitDao.updateDeviceByid(record);
        }
    }

    // 领取任务
    @Override
    public List<ServerTaskNotifyCommandReq> clientGetTaskStatus(String id) {
        logger.info("领取任务 clientGetTaskStatus deviceID = {}", id);

        Device device = autoxitDao.getDeviceBydeviceID(id);

        checkDeviceByDeviceId(device, id);

        return autoxitDao.getClientGetTaskByDeviceId(device.getDeviceid());

        //return JSON.toJSONString(list);
    }

    // 检查用户是否可以发送消息
    @Override
    public boolean clientCheckPushMessageStatus(ClientCheckPushMessageReq paramReq) {
        logger.info("clientCheckPushMessageStatus req = {}", paramReq);


        PushMessages pushMessages = autoxitDao.clientCheckPushMessageStatus(paramReq);

        if (pushMessages == null) {
            return true;
        }

        // 系统参数 不存在
        List<Dict> dicts = xrobotDao.dictList();
        if (dicts.isEmpty()) {
            return true;
        }

        Dict dict = dicts.get(0);

        // 发消息给用户时，同一消息一定时间内只能发送一次（时间间隔系统管理员设置，默认60分钟）
        Integer maxTaskIntervalValue = dict.getMaxTaskIntervalValue();

        // 上次发送时间  20:30
        Date prePushDate = pushMessages.getCreateDate();

        // 21:30
        Date nextBeginDate = Utility.addMinute(prePushDate, maxTaskIntervalValue);

        // 21:20  (那就不能发)
        Date now = new Date();

        if (nextBeginDate.compareTo(now) > 0) {
            logger.info("clientCheckPushMessageStatus contentmd5 = {},phone = {},usernumber = {},上次发送时间={},下次允许发送时间={},现在时间={}", paramReq.getMd5(), paramReq.getPhone(), paramReq.getUsernumber(), prePushDate, nextBeginDate, now);
            return false;
        } else {
            return true;
        }

    }

    // 上报发消息加群结果
    @Override
    @Transactional(rollbackFor = Exception.class, value = "phoenixTransactionManager")
    public String clientSubmitPushJoinGroupsStatus(ClientSubmitPushJoinGroupsReq paramReq) {
        logger.info("上报发消息加群结果 clientSubmitPushJoinGroupsStatus req = {}", paramReq);

        Device device = autoxitDao.getDeviceBydeviceID(paramReq.getId());

        checkDeviceByDeviceId(device, paramReq.getId());


        Tasks tasks = getTaskByTaskId(paramReq.getTaskID(), paramReq.getTaskclass());

        checkClientSubmitTaskInfo(tasks, paramReq);

        List<ClientSubmitPushJoinGroupsReq.GroupsInfo> join = paramReq.getJoin();


        SummaryJoinGroups record = new SummaryJoinGroups();
        record.setTaskid(paramReq.getTaskID());
        record.setTaskclass(paramReq.getTaskclass());
        record.setBatch(paramReq.getBatch());
        record.setDeviceid(paramReq.getId());
        record.setPhone(paramReq.getPhone());
        record.setKeywords(paramReq.getKeyword());
        record.preInsert(paramReq.getUser());

        if (join == null || join.isEmpty()) {
            record.setPass(0);
            record.setWait(0);
            autoxitDao.insertSummaryJoinGroups(record);
            return "";
        }

        int pass = 0;
        int wait = 0;


        for (ClientSubmitPushJoinGroupsReq.GroupsInfo groupsInfo : join) {

/*            private String groupID;
            private String groupname;
            private Integer state;*/

            // 0:待加入 1:等待审核 2:已加入
            if (groupsInfo.getState() == 1) {
                wait++;
            } else if (groupsInfo.getState() == 2) {
                pass++;
            } else {
                logger.warn("clientSubmitPushJoinGroupsStatus keyword={} 状态={}", paramReq.getKeyword(), groupsInfo.getState());
            }
        }

        record.setPass(pass);
        record.setWait(wait);
        autoxitDao.insertSummaryJoinGroups(record);

        Map<String, Object> dbParams = new HashMap<>();
        dbParams.put("nowDate", new Date());
        dbParams.put("userName", paramReq.getUser());
        dbParams.put("taskID", paramReq.getTaskID());
        dbParams.put("taskclass", paramReq.getTaskclass());
        dbParams.put("batch", paramReq.getBatch());
        dbParams.put("deviceID", paramReq.getId());
        dbParams.put("phone", paramReq.getPhone());
        dbParams.put("list", paramReq.getJoin());
        dbParams.put("keywords", paramReq.getKeyword());
        autoxitDao.insertPushJoinGroups(dbParams);


        return "";
    }

    // 上报发消息结果
    @Override
    public String clientSubmitPushMessagesStatus(ClientSubmitPushMessagesReq paramReq) {
        logger.info("上报发消息结果 clientSubmitPushMessagesStatus req = {}", paramReq);

        Device device = autoxitDao.getDeviceBydeviceID(paramReq.getId());

        checkDeviceByDeviceId(device, paramReq.getId());


        Tasks tasks = getTaskByTaskId(paramReq.getTaskID(), paramReq.getTaskclass());

        checkClientSubmitTaskInfo(tasks, paramReq);


        PushMessages record = new PushMessages();
        record.setTaskid(paramReq.getTaskID());
        record.setTaskclass(paramReq.getTaskclass());
        record.setBatch(paramReq.getBatch());
        record.setDeviceid(paramReq.getId());
        record.setPhone(paramReq.getPhone());
        record.setKeywords(paramReq.getKeyword());
        record.preInsert(paramReq.getUser());
        record.setGroupname(paramReq.getGroupname());
        record.setGroupname1(paramReq.getGroupname1());
        record.setUsernumber(paramReq.getUsernumber());
        record.setState(paramReq.getState());
        autoxitDao.insertPushMessages(record);


        return "";
    }

    // 上报评论加群结果
    @Override
    @Transactional(rollbackFor = Exception.class, value = "phoenixTransactionManager")
    public String clientSubmitCommentJoinGroupsStatus(ClientSubmitCommentJoinGroupsReq paramReq) {
        logger.info("clientSubmitCommentJoinGroupsStatus req = {}", paramReq);

        Device device = autoxitDao.getDeviceBydeviceID(paramReq.getId());

        checkDeviceByDeviceId(device, paramReq.getId());


        Tasks tasks = getTaskByTaskId(paramReq.getTaskID(), paramReq.getTaskclass());

        checkClientSubmitTaskInfo(tasks, paramReq);

        List<ClientSubmitCommentJoinGroupsReq.GroupsInfo> join = paramReq.getJoin();


        SummaryJoinGroups record = new SummaryJoinGroups();
        record.setTaskid(paramReq.getTaskID());
        record.setTaskclass(paramReq.getTaskclass());
        record.setBatch(paramReq.getBatch());
        record.setDeviceid(paramReq.getId());
        record.setPhone(paramReq.getPhone());
        record.setKeywords(paramReq.getKeyword());
        record.preInsert(paramReq.getUser());

        if (join == null || join.isEmpty()) {
            record.setPass(0);
            record.setWait(0);
            autoxitDao.insertSummaryJoinGroups(record);
            return "";
        }

        int pass = 0;
        int wait = 0;


        for (ClientSubmitCommentJoinGroupsReq.GroupsInfo groupsInfo : join) {

/*            private String groupID;
            private String groupname;
            private Integer state;*/

            // 0:待加入 1:等待审核 2:已加入
            if (groupsInfo.getState() == 1) {
                wait++;
            } else if (groupsInfo.getState() == 2) {
                pass++;
            } else {
                logger.warn("clientSubmitCommentJoinGroupsStatus keyword={} 状态={}", paramReq.getKeyword(), groupsInfo.getState());
            }
        }

        record.setPass(pass);
        record.setWait(wait);
        autoxitDao.insertSummaryJoinGroups(record);

        Map<String, Object> dbParams = new HashMap<>();
        dbParams.put("nowDate", new Date());
        dbParams.put("userName", paramReq.getUser());
        dbParams.put("taskID", paramReq.getTaskID());
        dbParams.put("taskclass", paramReq.getTaskclass());
        dbParams.put("batch", paramReq.getBatch());
        dbParams.put("deviceID", paramReq.getId());
        dbParams.put("phone", paramReq.getPhone());
        dbParams.put("list", paramReq.getJoin());
        dbParams.put("keywords", paramReq.getKeyword());
        autoxitDao.insertCommentJoinGroups(dbParams);


        return "";
    }

    @Override
    public String clientSubmitCommentsStatus(ClientSubmitCommentsReq paramReq) {
        logger.info("clientSubmitCommentsStatus req = {}", paramReq);

        Device device = autoxitDao.getDeviceBydeviceID(paramReq.getId());

        checkDeviceByDeviceId(device, paramReq.getId());


        Tasks tasks = getTaskByTaskId(paramReq.getTaskID(), paramReq.getTaskclass());

        checkClientSubmitTaskInfo(tasks, paramReq);


        Comments record = new Comments();
        record.setTaskid(paramReq.getTaskID());
        record.setTaskclass(paramReq.getTaskclass());
        record.setBatch(paramReq.getBatch());
        record.setDeviceid(paramReq.getId());
        record.setPhone(paramReq.getPhone());
        record.setKeywords(paramReq.getKeyword());
        record.preInsert(paramReq.getUser());
        record.setGroupname(paramReq.getGroupname());
        record.setGroupname1(paramReq.getGroupname1());
        record.setPoster(paramReq.getPoster());
        record.setState(paramReq.getState());
        autoxitDao.insertComments(record);

        return "";
    }

    @Override
    public String clientSubmitCreateGroupsStatus(ClientSubmitCreateGroupsReq paramReq) {
        logger.info("clientSubmitCreateGroupsStatus req = {}", paramReq);

        Device device = autoxitDao.getDeviceBydeviceID(paramReq.getId());

        checkDeviceByDeviceId(device, paramReq.getId());


        Tasks tasks = getTaskByTaskId(paramReq.getTaskID(), paramReq.getTaskclass());

        checkClientSubmitTaskInfo(tasks, paramReq);


        CreateGroups record = new CreateGroups();
        record.setTaskid(paramReq.getTaskID());
        record.setTaskclass(paramReq.getTaskclass());
        record.setBatch(paramReq.getBatch());
        record.setDeviceid(paramReq.getId());
        record.setPhone(paramReq.getPhone());
        record.preInsert(paramReq.getUser());
        record.setGroupid(paramReq.getGroupID());
        record.setGroupname(paramReq.getGroupname());
        record.setState(paramReq.getState());
        record.setPost(paramReq.getPost());

        autoxitDao.insertCreateGroups(record);

        return "";

    }

    @Override
    @Transactional(rollbackFor = Exception.class, value = "phoenixTransactionManager")
    public String clientSubmitTaskResponseStatus(ClientSubmitTaskResponseReq paramReq) {
        logger.info("clientSubmitTaskResponseStatus req = {}", paramReq);

        Device device = autoxitDao.getDeviceBydeviceID(paramReq.getId());

        checkDeviceByDeviceId(device, paramReq.getId());


        Tasks tasks = getTaskByTaskId(paramReq.getTaskID(), paramReq.getTaskclass());

        checkClientSubmitTaskInfo(tasks, paramReq);


        TaskDevices record = new TaskDevices();
        record.setError(paramReq.getError());
        record.setErrormsg(paramReq.getErrorMsg());
        record.preUpdate(paramReq.getUser());
        record.setState(2); // 执行完成
        TaskDevicesExample example = new TaskDevicesExample();
        example.createCriteria().andDeviceidEqualTo(paramReq.getId()).andTaskidEqualTo(paramReq.getTaskID());

        autoxitDao.updateTaskDevices(record, example);


        List<TaskDevices> list = autoxitDao.getTaskDevicesIsNotComplete(paramReq.getTaskID());

        if (list == null || list.isEmpty()) {

            TasksWithBLOBs tasksRecord = new TasksWithBLOBs();
            tasksRecord.setState(2);
            tasksRecord.preUpdate(paramReq.getUser());

            TasksExample tasksExample = new TasksExample();
            tasksExample.createCriteria().andTaskidEqualTo(paramReq.getTaskID());

            autoxitDao.updateTask(tasksRecord, tasksExample);
        }


        return "";
    }


    private void checkDeviceByDeviceId(Device device, String devicdId) {
        if (device == null) {
            logger.warn("clientGetTaskStatus deviceID={} 对应的设备不存在", devicdId);
            throw new XRobotException(ServerCode.SERVER_CODE_INT_98, "id = " + devicdId + "对应设备不存在");
        }

        if (device.getAllow() == 0) {
            logger.warn("clientGetTaskStatus deviceID={} 对应的设备 被禁用", devicdId);
            throw new XRobotException(ServerCode.SERVER_CODE_INT_3, "终端连接被拒绝，账号被禁用【认证失败】！");
        }
    }


    private void checkClientSubmitTaskInfo(Tasks tasks, BaseClientSubmitReq paramReq) {
        if (tasks == null) {
            logger.info("checkClientSubmitTaskInfo 任务id = {},taskClass={} 对应的任务不存在", paramReq.getTaskID(), paramReq.getTaskclass());
            throw new XRobotException(ServerCode.SERVER_CODE_INT_97, "任务ID taskID = " + paramReq.getTaskID() + " ,taskClass = " + paramReq.getTaskclass() + " 对应的任务不存在");
        }

        if (tasks.getBatch().intValue() != paramReq.getBatch()) {
            logger.info("clientSubmitPushJoinGroupsStatus 任务id={}, 服务端批次={}，请求的批次={}", paramReq.getTaskID(), tasks.getBatch(), paramReq.getBatch());
            throw new XRobotException(ServerCode.SERVER_CODE_INT_96, "任务ID taskID = " + paramReq.getTaskID() + " 对应的任务批次 batch = " + tasks.getBatch().intValue() + ", 请求参数批次 batch=" + paramReq.getBatch());
        }

        if (!StringUtils.equals(paramReq.getUser(), tasks.getCreateBy())) {
            logger.info("clientSubmitPushJoinGroupsStatus 任务id={}, 服务端用户={}，请求的用户={}", paramReq.getTaskID(), tasks.getCreateBy(), paramReq.getUser());
            throw new XRobotException(ServerCode.SERVER_CODE_INT_96, "任务ID taskID = " + paramReq.getTaskID() + " 对应的任务所属用户 user = " + tasks.getCreateBy() + ", 请求参数用户user=" + paramReq.getUser());
        }


    }

    public Tasks getTaskByTaskId(String taskId, String taskClass) {
        return autoxitDao.getTaskByTaskId(taskId, taskClass);
    }
}
