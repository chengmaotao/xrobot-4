package com.fairyland.xrobot.modular.xrobot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClientCheckPushMessageReq;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClinetLoginReq;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ServerTaskNotifyCommandReq;
import com.fairyland.xrobot.modular.xrobot.dao.AutoxitDao;
import com.fairyland.xrobot.modular.xrobot.dao.XrobotDao;
import com.fairyland.xrobot.modular.xrobot.domain.Device;
import com.fairyland.xrobot.modular.xrobot.domain.Dict;
import com.fairyland.xrobot.modular.xrobot.domain.PushMessages;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import com.fairyland.xrobot.modular.xrobot.service.AutoxitService;
import com.fairyland.xrobot.modular.xrobot.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

        if (device == null) {
            logger.warn("modifyDeviceStateByClientStata deviceID={} 对应的设备不存在", id);
            throw new XRobotException(98, "id = " + id + "对应设备不存在");
        }

        if (device.getAllow() == 0) {
            logger.warn("modifyDeviceStateByClientStata deviceID={} 对应的设备 被禁用", id);
            throw new XRobotException(3, "终端连接被拒绝，账号被禁用【认证失败】！");
        }

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


        autoxitDao.updateDeviceByid(record);

    }

    @Override
    public String clientGetTaskStatus(String id) {
        logger.info("clientGetTaskStatus deviceID = {}", id);

        Device device = autoxitDao.getDeviceBydeviceID(id);

        if (device == null) {
            logger.warn("clientGetTaskStatus deviceID={} 对应的设备不存在", id);
            throw new XRobotException(98, "id = " + id + "对应设备不存在");
        }

        if (device.getAllow() == 0) {
            logger.warn("clientGetTaskStatus deviceID={} 对应的设备 被禁用", id);
            throw new XRobotException(3, "终端连接被拒绝，账号被禁用【认证失败】！");
        }

        List<ServerTaskNotifyCommandReq> list = autoxitDao.getClientGetTaskByDeviceId(device.getDeviceid());

        return JSON.toJSONString(list);
    }

    @Override
    public String clientCheckPushMessageStatus(ClientCheckPushMessageReq paramReq) {
        logger.info("clientCheckPushMessageStatus req = {}", paramReq);


        PushMessages pushMessages = autoxitDao.clientCheckPushMessageStatus(paramReq);

        JSONObject response = new JSONObject();

        if (pushMessages == null) {
            response.put("push", true);
            return response.toJSONString();
        }

        // 系统参数 不存在
        List<Dict> dicts = xrobotDao.dictList();
        if (dicts.isEmpty()) {
            response.put("push", true);
            return response.toJSONString();
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
            response.put("push", false);
            return response.toJSONString();
        } else {
            response.put("push", true);
            return response.toJSONString();
        }

    }
}
