package com.fairyland.xrobot.modular.xrobot.service.impl;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.constant.XRobotCode;
import com.fairyland.xrobot.common.utils.PageUtils;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.system.domain.SysUser;
import com.fairyland.xrobot.modular.xrobot.autoxit.server.LinkerServer;
import com.fairyland.xrobot.modular.xrobot.dao.XrobotDao;
import com.fairyland.xrobot.modular.xrobot.domain.*;
import com.fairyland.xrobot.modular.xrobot.domain.req.*;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersInitResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersListResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.PageResult;
import com.fairyland.xrobot.modular.xrobot.domain.resp.QRCodeResp;
import com.fairyland.xrobot.modular.xrobot.exception.BusinessException;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import com.fairyland.xrobot.modular.xrobot.service.XrobotService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: fairyland->XrobotServiceImpl
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 11:30
 **/
@Service
public class XrobotServiceImpl extends BaseServiceImpl implements XrobotService {

    private Logger logger = LoggerFactory.getLogger(XrobotServiceImpl.class);


    @Autowired
    private LinkerServer robotServer;


    @Autowired
    private XrobotDao xrobotDao;

    @Override
    public PageResult deviceList(DeviceListReq paramReq) {
        logger.info("deviceList req = {}", paramReq);

        paramReq.setCurrentUser(getCurrentUser().getUserName());

        List<Device> list = xrobotDao.deviceList(paramReq);

        PageInfo<Device> pageInfo = new PageInfo<>(list);
        PageResult pageResult = PageUtils.getPageResult(pageInfo);

        return pageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, value = "phoenixTransactionManager")
    public void saveDevice(SaveDeviceReq paramReq) {

        logger.info("SaveDeviceReq req = {}", paramReq);

        paramReq.validate();


        SysUser user = getCurrentUser();

        Device record = new Device();
        record.setDevicesn(paramReq.getDevicesn());
        record.setPhone(paramReq.getPhone());
        record.setAccount(paramReq.getAccount());
        record.setPassword(paramReq.getPassword());
        record.setLogin(paramReq.getLogin());
        record.setAccount1(paramReq.getAccount1());
        record.setPassword1(paramReq.getPassword1());
        record.setLogin1(paramReq.getLogin1());
        record.setRemarks(paramReq.getRemarks());
        List<Device> lists = null;
        int num = 0;

        if (paramReq.getId() == null) {


            // 判断 终端设备应用编号 是否已经存在
            lists = xrobotDao.getDeviceListByDeviceSN(paramReq.getDevicesn(), user.getUserName());
            if (lists != null && lists.size() > 0) {
                logger.warn("SaveDeviceReq req = {},终端设备应用编号 已经存在,不能重复添加", paramReq);
                throw new BusinessException("终端设备应用编号: " + paramReq.getDevicesn() + " 已经存在，不能重复添加。");
            }

            // 判断 手机号 是否已经存在
            lists = xrobotDao.getDeviceListByPhone(paramReq.getPhone(), user.getUserName());
            if (lists != null && lists.size() > 0) {
                logger.warn("SaveDeviceReq req = {},终端设备手机号 已经存在,不能重复添加", paramReq);
                throw new BusinessException("终端设备手机号码: " + paramReq.getPhone() + " 已经存在，不能重复添加。");
            }

            // 新增
            record.setState(0);
            record.setToken(paramReq.getToken());
            record.setDeviceid(getSerializeVal());
            record.preInsert(user);
            num = xrobotDao.insertDevice(record);

        } else {
            // 修改

            Device oldInfo = xrobotDao.getDeviceInfoById(paramReq.getId(), user.getUserName());
            if (oldInfo != null && !StringUtils.equals(oldInfo.getDevicesn(), paramReq.getDevicesn())) {
                lists = xrobotDao.getDeviceListByDeviceSN(paramReq.getDevicesn(), user.getUserName());
                if (lists != null && lists.size() > 0) {
                    logger.warn("SaveDeviceReq2 req = {},终端设备应用编号 已经存在,不能重复添加", paramReq);
                    throw new BusinessException("终端设备应用编号: " + paramReq.getDevicesn() + " 已经存在，不能重复添加。");
                }
            }

            if (oldInfo != null && !StringUtils.equals(oldInfo.getPhone(), paramReq.getPhone())) {
                // 判断 手机号 是否已经存在
                lists = xrobotDao.getDeviceListByPhone(paramReq.getPhone(), user.getUserName());
                if (lists != null && lists.size() > 0) {
                    logger.warn("SaveDeviceReq2 req = {},终端设备手机号 已经存在,不能重复添加", paramReq);
                    throw new BusinessException("终端设备手机号码: " + paramReq.getPhone() + " 已经存在，不能重复添加。");
                }
            }

            record.preUpdate(user);

            DeviceExample example = new DeviceExample();
            example.createCriteria().andIdEqualTo(paramReq.getId()).andCreateByEqualTo(user.getUserName());
            num = xrobotDao.updateDevice(record, example);

        }

        if (num < 1) {
            logger.warn("SaveDeviceReq 保存终端设备 影响行数num = {}", num);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

    }


    @Override
    public void delDevice(DelDeviceReq paramReq) {
        logger.info("delDeviceReq paramReq = {}", paramReq);

        paramReq.validate();


        // TODO CTC 连接状态 不允许删除
/*        boolean isActive = robotServer.sessionIsActive(paramReq.getClientId());

        if (isActive) {
            logger.warn("客户端={} 连接状态 不允许删除", paramReq.getClientId());
            throw new BusinessException("该客户端是连接状态 不允许删除。若要删除 请先去断开连接 再执行删除操作");
        }*/

        SysUser user = getCurrentUser();

        DeviceExample example = new DeviceExample();
        example.createCriteria().andIdEqualTo(paramReq.getId()).andCreateByEqualTo(user.getUserName());

        Device record = new Device();
        record.setDelFlag(XRobotCode.DEL_1);
        record.preUpdate(user);

        xrobotDao.updateDevice(record, example);
    }

    @Override
    public void resetDeviceState(ResetDeviceStateReq paramReq) {
        logger.info("resetDeviceState req = {}", paramReq);

        paramReq.validate();

        SysUser user = getCurrentUser();


        DeviceExample example = new DeviceExample();
        example.createCriteria().andIdEqualTo(paramReq.getId()).andDeviceidEqualTo(paramReq.getDeviceid()).andPhoneEqualTo(paramReq.getPhone()).andCreateByEqualTo(user.getUserName());


        Device record = new Device();
        record.setAccount(paramReq.getAccount());
        record.setPassword(paramReq.getPassword());
        record.setLogin(paramReq.getLogin());
        record.setAccount1(paramReq.getAccount1());
        record.setPassword1(paramReq.getPassword1());
        record.setLogin1(paramReq.getLogin1());
        record.setState(0);
        record.preUpdate(user);

        xrobotDao.resetDeviceState(example, record);

    }

    @Override
    public Device getDeviceInfoById(DelDeviceReq paramReq) {
        logger.info("getDeviceInfoById paramReq = {}", paramReq);

        paramReq.validate();

        return xrobotDao.getDeviceInfoById(paramReq.getId(), getCurrentUser().getUserName());
    }

    @Override
    public PageResult deviceGroupList(DeviceGroupListReq paramReq) {
        logger.info("deviceGroupList req = {}", paramReq);

        paramReq.setCurrentUser(getCurrentUser().getUserName());
        List<DeviceGroup> list = xrobotDao.deviceGroupList(paramReq);

        PageInfo<DeviceGroup> pageInfo = new PageInfo<>(list);
        PageResult pageResult = PageUtils.getPageResult(pageInfo);

        return pageResult;
    }

    @Override
    public DeviceGroup getDeviceGroupInfoById(DelDeviceGroupReq paramReq) {
        logger.info("getDeviceGroupInfoById paramReq = {}", paramReq);

        paramReq.validate();

        return xrobotDao.getDeviceGroupInfoById(paramReq.getId(), getCurrentUser().getUserName());
    }

    @Override
    public void saveDeviceGroup(SaveDeviceGroupReq paramReq) {
        logger.info("SaveDeviceGroupReq req = {}", paramReq);

        paramReq.validate();

        SysUser user = getCurrentUser();

        DeviceGroup record = new DeviceGroup();
        record.setGroupname(paramReq.getGroupname());
        record.setRemarks(paramReq.getRemarks());
        List<DeviceGroup> lists = null;
        int num = 0;

        if (paramReq.getId() == null) {

            // 判断 分组名称 是否已经存在
            lists = xrobotDao.getDeviceGroupListByName(paramReq.getGroupname(), user.getUserName());
            if (lists != null && lists.size() > 0) {
                logger.warn("SaveDeviceReq req = {},终端设备分组名称 已经存在,不能重复添加", paramReq);
                throw new BusinessException("终端设备分组名称: " + paramReq.getGroupname() + " 已经存在，不能重复添加。");
            }

            // 新增
            record.setGroupid(getSerializeVal());
            record.preInsert(user);
            num = xrobotDao.insertDeviceGroup(record);

        } else {
            // 修改

            DeviceGroup oldInfo = xrobotDao.getDeviceGroupInfoById(paramReq.getId(), user.getUserName());
            if (oldInfo != null && !StringUtils.equals(oldInfo.getGroupname(), paramReq.getGroupname())) {
                lists = xrobotDao.getDeviceGroupListByName(paramReq.getGroupname(), user.getUserName());
                if (lists != null && lists.size() > 0) {
                    logger.warn("SaveDeviceReq2 req = {},终端设备分组名称 已经存在,不能重复添加", paramReq);
                    throw new BusinessException("终端设备分组名称: " + paramReq.getGroupname() + " 已经存在，不能重复添加。");
                }
            }

            record.preUpdate(user);

            DeviceGroupExample example = new DeviceGroupExample();
            example.createCriteria().andIdEqualTo(paramReq.getId()).andCreateByEqualTo(user.getUserName());

            num = xrobotDao.updateDeviceGroup(record, example);

        }

        if (num < 1) {
            logger.warn("saveDeviceGroup 终端设备分组 影响行数num = {}", num);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }
    }

    @Override
    public void delDeviceGroup(DelDeviceGroupReq paramReq) {
        logger.info("delDeviceGroup paramReq = {}", paramReq);

        paramReq.validate();

        SysUser user = getCurrentUser();

        DeviceGroupExample example = new DeviceGroupExample();
        example.createCriteria().andIdEqualTo(paramReq.getId()).andCreateByEqualTo(user.getUserName());

        DeviceGroup record = new DeviceGroup();
        record.setDelFlag(XRobotCode.DEL_1);
        record.preUpdate(user);

        xrobotDao.updateDeviceGroup(record, example);
    }

    @Override
    public List<Device> deviceAllList() {

        SysUser user = getCurrentUser();

        List<Device> list = xrobotDao.deviceAllList(user.getUserName());
        return list;
    }

    @Override
    public List<DeviceGroup> deviceGroupAllList() {


        List<DeviceGroup> list = xrobotDao.deviceGroupAllList(getCurrentUser().getUserName());

        return list;
    }

    @Override
    public PageResult deviceGroupMembersList(DeviceGroupMembersListReq paramReq) {

        logger.info("deviceGroupMembersList req = {}", paramReq);

        paramReq.setCurrentUser(getCurrentUser().getUserName());
        List<DeviceGroupMembersListResp> list = xrobotDao.deviceGroupMembersList(paramReq);

        PageInfo<DeviceGroupMembersListResp> pageInfo = new PageInfo<>(list);
        PageResult pageResult = PageUtils.getPageResult(pageInfo);

        return pageResult;
    }

    @Override
    public void delDeviceGroupMembers(DelDeviceGroupMembersReq paramReq) {
        logger.info("delDeviceGroupMembers paramReq = {}", paramReq);

        paramReq.validate();

        xrobotDao.delDeviceGroupMembers(paramReq.getId(), getCurrentUser().getUserName());
    }

    @Override
    public List<DeviceGroupMembersInitResp> saveDeviceGroupMembersInit(DeviceGroupMembersInitReq paramReq) {
        logger.info("saveDeviceGroupMembersInit paramReq = {}", paramReq);

        paramReq.validate();

        paramReq.setCurrentUser(getCurrentUser().getUserName());
        return xrobotDao.saveDeviceGroupMembersInit(paramReq);

    }

    @Override
    public void saveDeviceGroupMembers(DeviceGroupMembersReq paramReq) {
        logger.info("saveDeviceGroupMembers paramReq = {}", paramReq);

        paramReq.validate();

        paramReq.setUserName(getCurrentUser().getUserName());

        xrobotDao.saveDeviceGroupMembers(paramReq);


    }

    @Override
    public QRCodeResp getQRCodeJsonById(DelDeviceReq paramReq) {
        logger.info("getDeviceInfoById paramReq = {}", paramReq);

        paramReq.validate();

        paramReq.setCurrentUser(getCurrentUser().getUserName());
        return xrobotDao.getQRCodeJsonById(paramReq);

    }

    @Override
    public List<Dict> dictList() {
        return xrobotDao.dictList();
    }

    @Override
    public void saveDict(SaveDictReq paramReq) {

        logger.info("saveDict paramReq = {}", paramReq);

        paramReq.validate();

        Dict record = new Dict();
        record.setId(paramReq.getId());
        record.setMaxAddGroupValue(paramReq.getMaxAddGroupValue());
        record.setMaxSearchKeyValue(paramReq.getMaxSearchKeyValue());
        record.setMaxTaskIntervalValue(paramReq.getMaxTaskIntervalValue());
        record.setRemarks(paramReq.getRemarks());
        record.preUpdate(getCurrentUser());
        xrobotDao.saveDict(record);
    }
}
