package com.fairyland.xrobot.modular.xrobot.dao.impl;

import com.fairyland.xrobot.common.constant.XRobotCode;
import com.fairyland.xrobot.modular.xrobot.dao.AutoxitDao;
import com.fairyland.xrobot.modular.xrobot.dao.mapper.DeviceMapper;
import com.fairyland.xrobot.modular.xrobot.domain.Device;
import com.fairyland.xrobot.modular.xrobot.domain.DeviceExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
