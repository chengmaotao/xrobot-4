package com.fairyland.xrobot.modular.xrobot.dao;

import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClientCheckPushMessageReq;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ServerTaskNotifyCommandReq;
import com.fairyland.xrobot.modular.xrobot.domain.Device;
import com.fairyland.xrobot.modular.xrobot.domain.PushMessages;

import java.util.List;

public interface AutoxitDao {
    Device getDeviceBydeviceID(String id);

    void updateDeviceByid(Device record);

    List<ServerTaskNotifyCommandReq> getClientGetTaskByDeviceId(String deviceid);

    PushMessages clientCheckPushMessageStatus(ClientCheckPushMessageReq paramReq);
}
