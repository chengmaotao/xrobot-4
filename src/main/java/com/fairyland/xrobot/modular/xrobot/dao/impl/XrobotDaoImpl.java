package com.fairyland.xrobot.modular.xrobot.dao.impl;

import com.fairyland.xrobot.common.constant.XRobotCode;
import com.fairyland.xrobot.modular.xrobot.dao.XrobotDao;
import com.fairyland.xrobot.modular.xrobot.dao.mapper.DeviceGroupMapper;
import com.fairyland.xrobot.modular.xrobot.dao.mapper.DeviceGroupMembersMapper;
import com.fairyland.xrobot.modular.xrobot.dao.mapper.DeviceMapper;
import com.fairyland.xrobot.modular.xrobot.domain.*;
import com.fairyland.xrobot.modular.xrobot.domain.req.DeviceGroupListReq;
import com.fairyland.xrobot.modular.xrobot.domain.req.DeviceGroupMembersListReq;
import com.fairyland.xrobot.modular.xrobot.domain.req.DeviceGroupMembersReq;
import com.fairyland.xrobot.modular.xrobot.domain.req.DeviceListReq;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersInitResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersListResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.QRCodeResp;
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
    public int updateDevice(Device record) {
        return deviceMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Device> getDeviceListByDeviceSN(String devicesn) {

        DeviceExample example = new DeviceExample();
        example.createCriteria().andDevicesnEqualTo(devicesn).andDelFlagEqualTo(XRobotCode.DEL_0);

        List<Device> list = deviceMapper.selectByExample(example);

        return list;
    }

    @Override
    public List<Device> getDeviceListByPhone(String phone) {
        DeviceExample example = new DeviceExample();
        example.createCriteria().andPhoneEqualTo(phone).andDelFlagEqualTo(XRobotCode.DEL_0);

        List<Device> list = deviceMapper.selectByExample(example);

        return list;
    }

    @Override
    public void delDevice(Device record) {

        deviceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void resetDeviceState(DeviceExample example, Device record) {
        deviceMapper.updateByExample(record, example);
    }

    @Override
    public Device getDeviceInfoById(Long id) {
        return deviceMapper.selectByPrimaryKey(id);
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
    public DeviceGroup getDeviceGroupInfoById(Long id) {
        return deviceGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DeviceGroup> getDeviceGroupListByName(String groupname) {

        DeviceGroupExample example = new DeviceGroupExample();
        example.createCriteria().andGroupnameEqualTo(groupname).andDelFlagEqualTo(XRobotCode.DEL_0);

        return deviceGroupMapper.selectByExample(example);
    }

    @Override
    public int insertDeviceGroup(DeviceGroup record) {
        return deviceGroupMapper.insertSelective(record);
    }

    @Override
    public int updateDeviceGroup(DeviceGroup record) {
        return deviceGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void delDeviceGroup(DeviceGroup record) {
        deviceGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Device> deviceAllList() {

        DeviceExample example = new DeviceExample();
        example.createCriteria().andDelFlagEqualTo(XRobotCode.DEL_0);
        example.setOrderByClause(" create_date desc");
        List<Device> list = deviceMapper.selectByExample(example);

        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<DeviceGroup> deviceGroupAllList() {
        DeviceGroupExample example = new DeviceGroupExample();
        example.createCriteria().andDelFlagEqualTo(XRobotCode.DEL_0);
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
    public void delDeviceGroupMembers(Long id) {
        deviceGroupMembersMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<DeviceGroupMembersInitResp> saveDeviceGroupMembersInit(String groupid) {
        return deviceGroupMembersMapper.saveDeviceGroupMembersInit(groupid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, value = "phoenixTransactionManager")
    public void saveDeviceGroupMembers(DeviceGroupMembersReq paramReq) {

        DeviceGroupMembersExample delexample = new DeviceGroupMembersExample();
        delexample.createCriteria().andGroupidEqualTo(paramReq.getGroupid());

        deviceGroupMembersMapper.deleteByExample(delexample);

        List<String> deviceids = paramReq.getDeviceids();

        if(deviceids == null || deviceids.isEmpty()){
            return;
        }

        Map<String,Object> params = new HashMap<>();

        params.put("groupid",paramReq.getGroupid());
        params.put("list",deviceids);
        params.put("nowDate",new Date());
        params.put("userName",paramReq.getUserName());

        deviceGroupMembersMapper.batchInsertData(params);

    }

    @Override
    public QRCodeResp getQRCodeJsonById(Long id) {
        return deviceMapper.getQRCodeJsonById(id);
    }
}
