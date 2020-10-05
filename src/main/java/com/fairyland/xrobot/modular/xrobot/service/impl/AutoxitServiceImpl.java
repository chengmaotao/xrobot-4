package com.fairyland.xrobot.modular.xrobot.service.impl;

import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClinetLoginReq;
import com.fairyland.xrobot.modular.xrobot.dao.AutoxitDao;
import com.fairyland.xrobot.modular.xrobot.dao.XrobotDao;
import com.fairyland.xrobot.modular.xrobot.domain.Device;
import com.fairyland.xrobot.modular.xrobot.service.AutoxitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int modifyDeviceStateByClientStata(String id, String status) {

/*        monitorClientAppStatus.put("0", "未就绪");
        monitorClientAppStatus.put("100", "正常");
        monitorClientAppStatus.put("200", "客户端已暂停");

        monitorClientAppStatus.put("300", "Facebook账号被禁用");
        monitorClientAppStatus.put("301", "WhatsApp账号被禁用");*/

        logger.info("modifyDeviceStateByClientStata deviceID = {},status = {}", id, status);

        Device device = autoxitDao.getDeviceBydeviceID(id);

        if (device == null) {
            logger.warn("modifyDeviceStateByClientStata deviceID={} 对应的设备不存在", id);
            return 99;
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

        return 1;


    }
}
