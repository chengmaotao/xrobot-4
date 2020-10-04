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
import com.fairyland.xrobot.modular.xrobot.domain.resp.*;
import com.fairyland.xrobot.modular.xrobot.exception.BusinessException;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import com.fairyland.xrobot.modular.xrobot.service.XrobotService;
import com.fairyland.xrobot.modular.xrobot.utils.Utility;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

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


    @Value("${picture_max_size}")
    private int picture_max_size;

    @Value("#{'${picture_suffix}'.split(',')}")
    private List<String> picture_suffix;


    @Value("${xrobot.file.uploa.path}")
    private String baseUploadPath;


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

            if (oldInfo == null) {
                logger.warn("SaveDeviceReq2 req = {},终端设备应用 不存在", paramReq);
                throw new XRobotException(ErrorCode.SYS_FAIL);
            }

            if (!StringUtils.equals(oldInfo.getDevicesn(), paramReq.getDevicesn())) {
                lists = xrobotDao.getDeviceListByDeviceSN(paramReq.getDevicesn(), user.getUserName());
                if (lists != null && lists.size() > 0) {
                    logger.warn("SaveDeviceReq2 req = {},终端设备应用编号 已经存在,不能重复添加", paramReq);
                    throw new BusinessException("终端设备应用编号: " + paramReq.getDevicesn() + " 已经存在，不能重复添加。");
                }
            }

            if (!StringUtils.equals(oldInfo.getPhone(), paramReq.getPhone())) {
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

            if (oldInfo == null) {
                logger.warn("SaveDeviceReq2 req = {},终端设备分组 不存在", paramReq);
                throw new XRobotException(ErrorCode.SYS_FAIL);

            }
            if (!StringUtils.equals(oldInfo.getGroupname(), paramReq.getGroupname())) {
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

    @Override
    public List<TaskDict> taskDictList() {


        return xrobotDao.taskDictList();
    }

    @Override
    public PageResult taskList(TaskListReq paramReq) {
        logger.info("taskList paramReq = {}", paramReq);
        paramReq.setCurrentUser(getCurrentUser().getUserName());

        List<TasksWithBLOBs> list = xrobotDao.taskList(paramReq);

        PageInfo<TasksWithBLOBs> pageInfo = new PageInfo<>(list);
        PageResult pageResult = PageUtils.getPageResult(pageInfo);

        return pageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, value = "phoenixTransactionManager")
    public void saveTask(SaveTaskReq paramReq, HttpServletRequest request) {
        logger.info("saveTask paramReq = {}", paramReq);

        paramReq.validate();

        String newFilePath = null;

        Iterator<String> files = ((MultipartHttpServletRequest) request).getFileNames();

        while (files.hasNext()) {
            String fName = files.next();
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile(fName);

            if (file != null) {
                // 文件名称
                String originalFilename = file.getOriginalFilename();

                // 源文件后缀
                String fileSuffix = getFileSuffix(originalFilename);

                // 校验 图片格式 是否支持
                if (fileSuffix == null || !picture_suffix.contains(fileSuffix)) {
                    logger.warn("saveTask  上传的文件格式不支持 = {}", originalFilename);
                    throw new XRobotException(ErrorCode.ERROR_CODE_21);
                }

                long pictureMaxSize = picture_max_size * 1048576;

                // 头像大小 超过最大值
                if (file.getSize() > pictureMaxSize) {
                    logger.warn("saveTask 上传的文件格式不支持{}", file.getSize());
                    throw new XRobotException(ErrorCode.ERROR_CODE_20);
                }


                String filePath = baseUploadPath + Utility.get32UUID() + originalFilename;

                File dest = new File(filePath);

                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }

                try {
                    file.transferTo(dest);  // 文件上传到 服务器
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("saveTask 文件上传失败: ex = {}", e);
                }

                newFilePath = filePath;
                break;
            }
        }

        SysUser user = getCurrentUser();

        TasksWithBLOBs record = new TasksWithBLOBs();
        record.setTaskclass(paramReq.getTaskclass());
        record.setKeywords(paramReq.getKeywords());
        record.setContent(paramReq.getContent());
        record.setMd5(Utility.getMd5(paramReq.getContent()));

        if (StringUtils.isNotEmpty(newFilePath)) {
            record.setCover(newFilePath);
        }

        int num = 0;
        // 新增
        if (StringUtils.isEmpty(paramReq.getTaskid())) {

            // 创建群组发帖任务
            if (StringUtils.equals(paramReq.getTaskclass(), "100003")) {

                if (StringUtils.isEmpty(newFilePath)) {
                    logger.warn("saveTask 任务表 封面链接图片  cover = {} 不正确", newFilePath);
                    throw new XRobotException(ErrorCode.ERROR_CODE_5);
                }
            }

            record.setBatch(0);
            record.setState(0); // 0:新创建
            record.setTaskid(getSerializeVal());
            record.preInsert(user);

            num = xrobotDao.insertTasks(record);

            Map<String, Object> dbParams = new HashMap<>();
            dbParams.put("nowDate", new Date());
            dbParams.put("username", user.getUserName());
            dbParams.put("taskID", record.getTaskid());
            dbParams.put("list", paramReq.getDeviceidList());

            // 任务执行终端表
            xrobotDao.insertTasksDevices(dbParams);

        } else {

            TasksWithBLOBs oldInfo = xrobotDao.getTaskInfoById(paramReq.getTaskid(), user.getUserName());

            if (oldInfo == null) {
                logger.warn("saveTask 任务不存在  id = {}，username={}", paramReq.getTaskid(), user.getUserName());
                throw new XRobotException(ErrorCode.SYS_FAIL);
            }

            if (oldInfo.getState() == 1) {
                logger.warn("saveTask 任务表 执行中的任务不允许修改  id = {}", paramReq.getTaskid());
                throw new BusinessException("任务正在执行中，暂不允许修改");
            }

            record.preUpdate(user);
            record.setId(oldInfo.getId());
            num = xrobotDao.updateTasks(record);


            xrobotDao.delTasksDevicesByTaskId(oldInfo.getTaskid(), user.getUserName());


            Map<String, Object> dbParams = new HashMap<>();
            dbParams.put("nowDate", new Date());
            dbParams.put("username", user.getUserName());
            dbParams.put("taskID", oldInfo.getTaskid());
            dbParams.put("list", paramReq.getDeviceidList());
            // 任务执行终端表
            xrobotDao.insertTasksDevices(dbParams);

        }

        if (num < 1) {
            logger.warn("saveTask 任务表 影响行数num = {}", num);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }


    }

    @Override
    public void delTask(DelTaskReq paramReq) {
        logger.info("delTask paramReq = {}", paramReq);

        paramReq.validate();

        SysUser user = getCurrentUser();


        TasksWithBLOBs oldInfo = xrobotDao.getTaskInfoById(paramReq.getTaskid(), user.getUserName());

        if (oldInfo == null) {
            logger.warn("delTask 任务不存在  id = {}，username={}", paramReq.getTaskid(), user.getUserName());
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

        if (oldInfo.getState() == 1) {
            logger.warn("delTask 任务表 执行中的任务不允许修改  id = {}", paramReq.getTaskid());
            throw new BusinessException("任务正在执行中，暂不允许删除");
        }


        TasksWithBLOBs record = new TasksWithBLOBs();
        record.setDelFlag(XRobotCode.DEL_1);
        record.preUpdate(user);
        record.setId(oldInfo.getId());

        xrobotDao.updateTasks(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, value = "phoenixTransactionManager")
    public void exeTask(ExeTaskReq paramReq) {
        logger.info("exeTask paramReq = {}", paramReq);

        paramReq.validate();

        SysUser user = getCurrentUser();


        TasksWithBLOBs oldInfo = xrobotDao.getTaskInfoById(paramReq.getTaskid(), user.getUserName());

        if (oldInfo == null) {
            logger.warn("exeTask 任务不存在  id = {}，username={}", paramReq.getTaskid(), user.getUserName());
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

        if (oldInfo.getState() == 1) {
            logger.warn("exeTask 任务表 任务已经在执行中了  id = {}", paramReq.getTaskid());
            throw new BusinessException("任务已经在执行中了");
        }


        // 修改任务表为 1:执行中
        Tasks record = new Tasks();
        record.preUpdate(user);
        record.setId(oldInfo.getId());
        record.setCreateBy(user.getUserName());
        xrobotDao.exeTask(record);


        // TODO SERVER_TASKNOTIFY_COMMAND 修改 任务执行终端表 为 执行中
    }

    @Override
    public PageResult taskDevicesList(TaskDevicesListReq paramReq) {
        logger.info("taskDevicesList paramReq = {}", paramReq);
        paramReq.setCurrentUser(getCurrentUser().getUserName());

        List<TaskDevices> list = xrobotDao.taskDevicesList(paramReq);

        PageInfo<TaskDevices> pageInfo = new PageInfo<>(list);
        PageResult pageResult = PageUtils.getPageResult(pageInfo);

        return pageResult;
    }

    @Override
    public Map<String, Object> saveTaskInit(SaveTaskInitReq paramReq) {
        logger.info("saveTaskInit paramReq = {}", paramReq);


        Map<String, Object> resp = new HashMap<>();
        if (StringUtils.isEmpty(paramReq.getTaskid())) {

            // 所有的设备列表
            List<Device> devices = deviceAllList();
            resp.put("devices", resp);
            // 所有的分组信息
            List<DeviceGroup> deviceGroups = deviceGroupAllList();
            resp.put("deviceGroups", deviceGroups);
        } else {

            paramReq.setCurrentUser(getCurrentUser().getUserName());

            List<SaveTaskInitResp> list = xrobotDao.saveTaskInit(paramReq);

            TasksWithBLOBs taskInfo = xrobotDao.getTaskInfoById(paramReq.getTaskid(), getCurrentUser().getUserName());

            resp.put("taskInfo", taskInfo);
            resp.put("initRespList", list);

        }

        return resp;

    }

    @Override
    public List<DeviceGroupMembersListResp> deviceGroupMembersAllList(DeviceGroupMembersListReq paramReq) {


        logger.info("deviceGroupMembersAllList req = {}", paramReq);

        paramReq.setCurrentUser(getCurrentUser().getUserName());
        List<DeviceGroupMembersListResp> list = xrobotDao.deviceGroupMembersAllList(paramReq);

        return list;

    }


    private String getFileSuffix(String originalFilename) {

        if (StringUtils.isEmpty(originalFilename)) {
            return null;
        }

        int pos = originalFilename.lastIndexOf(".");
        if (pos == -1) {
            return null;
        }

        String fileSuffix = originalFilename.substring(pos + 1);
        return fileSuffix.toLowerCase();
    }
}
