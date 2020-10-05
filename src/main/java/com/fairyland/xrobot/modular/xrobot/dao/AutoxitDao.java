package com.fairyland.xrobot.modular.xrobot.dao;

import com.fairyland.xrobot.modular.xrobot.domain.Device;

public interface AutoxitDao {
    Device getDeviceBydeviceID(String id);

    void updateDeviceByid(Device record);
}
