package com.fairyland.xrobot.modular.xrobot.dao.impl;

import com.fairyland.xrobot.common.constant.XRobotCode;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClientCheckPushMessageReq;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ServerTaskNotifyCommandReq;
import com.fairyland.xrobot.modular.xrobot.dao.AutoxitDao;
import com.fairyland.xrobot.modular.xrobot.dao.mapper.DeviceMapper;
import com.fairyland.xrobot.modular.xrobot.dao.mapper.PushMessagesMapper;
import com.fairyland.xrobot.modular.xrobot.dao.mapper.TaskDevicesMapper;
import com.fairyland.xrobot.modular.xrobot.domain.Device;
import com.fairyland.xrobot.modular.xrobot.domain.DeviceExample;
import com.fairyland.xrobot.modular.xrobot.domain.PushMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

       if(list == null || list.isEmpty()){
           return null;
       }
        return list.get(0);
    }
}
