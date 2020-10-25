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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
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


    @Value("${app.uploa.path}")
    private String baseAppUploadPath;

    @Value("#{'${app_suffix}'.split(',')}")
    private List<String> app_suffix;


    @Value("${export.username.model}")
    private String exportUsernameModel;


    @Value("${excel.model.path}")
    private String outExcelPath;


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
        record.setPhone(paramReq.getPhone().replace(" ", ""));
        record.setAccount(paramReq.getAccount());
        record.setPassword(paramReq.getPassword());
        record.setLogin(paramReq.getLogin());
        record.setAccount1(paramReq.getAccount1());
        record.setPassword1(paramReq.getPassword1());
        record.setLogin1(paramReq.getLogin1());
        record.setRemarks(paramReq.getRemarks());
        record.setRole(paramReq.getRole());
        List<Device> lists = null;
        int num = 0;

        if (paramReq.getId() == null) {

            // 判断 终端设备应用编号 是否已经存在
            lists = xrobotDao.getDeviceListByDeviceSN(paramReq.getDevicesn(), user.getUserName());
            if (lists != null && lists.size() > 0) {
                logger.warn("SaveDeviceReq req = {},终端设备应用编号 已经存在,不能重复添加", paramReq);
                throw new BusinessException("终端设备应用编号: " + paramReq.getDevicesn() + " 已经存在，请使用其他编号");
            }

/*            // 判断 手机号 是否已经存在
            lists = xrobotDao.getDeviceListByPhone(paramReq.getPhone(), user.getUserName());
            if (lists != null && lists.size() > 0) {
                logger.warn("SaveDeviceReq req = {},终端设备手机号 已经存在,不能重复添加", paramReq);
                throw new BusinessException("终端设备手机号码: " + paramReq.getPhone() + " 已经存在，请使用其他手机号。");
            }*/

            // 新增
            record.setState(0);
            record.setToken(Utility.get32UUID());
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

            // 连接中的设备 不允许修改
            if (robotServer.sessionIsActive(oldInfo.getDeviceid())) {
                logger.warn("SaveDeviceReq2 req = {},终端设备应用编号 登录中的状态 不允许修改", paramReq);
                throw new BusinessException("该客户端是连接状态 不允许修改。若要修改 请先去断开连接 再执行修改操作");
            }

            if (!StringUtils.equals(oldInfo.getDevicesn(), paramReq.getDevicesn())) {
                lists = xrobotDao.getDeviceListByDeviceSN(paramReq.getDevicesn(), user.getUserName());
                if (lists != null && lists.size() > 0) {
                    logger.warn("SaveDeviceReq2 req = {},终端设备应用编号 已经存在,不能重复添加", paramReq);
                    throw new BusinessException("终端设备应用编号: " + paramReq.getDevicesn() + " 已经存在，请使用其他编号");
                }
            }

/*            if (!StringUtils.equals(oldInfo.getPhone(), paramReq.getPhone())) {
                // 判断 手机号 是否已经存在
                lists = xrobotDao.getDeviceListByPhone(paramReq.getPhone(), user.getUserName());
                if (lists != null && lists.size() > 0) {
                    logger.warn("SaveDeviceReq2 req = {},终端设备手机号 已经存在,不能重复添加", paramReq);
                    throw new BusinessException("终端设备手机号码: " + paramReq.getPhone() + " 已经存在，请使用其他手机号。");
                }
            }*/

            // true 重置
            if (paramReq.isResetState()) {
                record.setState(0);
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
        SysUser user = getCurrentUser();
        Device oldInfo = xrobotDao.getDeviceInfoById(paramReq.getId(), user.getUserName());

        if (oldInfo == null) {
            logger.warn("delDeviceReq req = {},终端设备应用 不存在", paramReq);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

        if (robotServer.sessionIsActive(oldInfo.getDeviceid())) {
            logger.warn("delDeviceReq req = {},终端设备应用编号 连接的状态 不允许删除", paramReq);
            throw new BusinessException("该客户端是连接状态 不允许删除。若要删除 请先去断开连接 再执行删除操作");
        }

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

        Device oldInfo = xrobotDao.getDeviceInfoById(paramReq.getId(), user.getUserName());

        if (oldInfo == null) {
            logger.warn("resetDeviceState req = {},终端设备应用 不存在", paramReq);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

        if (robotServer.sessionIsActive(oldInfo.getDeviceid())) {
            logger.warn("resetDeviceState req = {},终端设备应用编号 连接的状态 不允许重置操作", paramReq);
            throw new BusinessException("该客户端是连接状态 不允许重置。若要重置 请先去断开连接 再执行重置操作");
        }

        DeviceExample example = new DeviceExample();
        example.createCriteria().andIdEqualTo(paramReq.getId()).andDeviceidEqualTo(paramReq.getDeviceid()).andCreateByEqualTo(user.getUserName());


        Device record = new Device();
        record.setPhone(paramReq.getPhone());
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
                throw new BusinessException("终端设备分组名称: " + paramReq.getGroupname() + " 已经存在，请使用其他分组名称。");
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
                    throw new BusinessException("终端设备分组名称: " + paramReq.getGroupname() + " 已经存在，请使用其他分组名称。");
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
    public List<Device> deviceAllList(Integer role) {

        SysUser user = getCurrentUser();

        return xrobotDao.deviceAllList(user.getUserName(), role);

    }

    @Override
    public List<DeviceGroup> deviceGroupAllList() {

        return xrobotDao.deviceGroupAllList(getCurrentUser().getUserName());
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


        if (StringUtils.equals(paramReq.getTaskclass(), "100001") || StringUtils.equals(paramReq.getTaskclass(), "100004") || StringUtils.equals(paramReq.getTaskclass(), "100003")) {
            // 系统参数 不存在
            List<Dict> dicts = xrobotDao.dictList();

            if (!dicts.isEmpty()) {
                Dict dict = dicts.get(0);

                // 创建群任务支持批量创建群（最大值系统管理员设置，默认100）
                Integer maxAddGroupValue = dict.getMaxAddGroupValue();

                // 任务创建时支持多个搜索关键字问题（最大值系统管理员设置，默认100）
                Integer maxSearchKeyValue = dict.getMaxSearchKeyValue();

                int len = paramReq.getKeywords().split("(\\r\\n|\\r|\\n|\\n\\r)").length;
                if (StringUtils.equals(paramReq.getTaskclass(), "100003")) {

                    if (len > maxAddGroupValue) {
                        logger.warn("saveTask keywords 回车换行 长度 超过最大值了");
                        throw new BusinessException("创建群任务支持批量创建群 超过了最大值 " + maxAddGroupValue);
                    }
                } else if (StringUtils.equals(paramReq.getTaskclass(), "100001") || StringUtils.equals(paramReq.getTaskclass(), "100004")) {

                    if (len > maxSearchKeyValue) {
                        logger.warn("saveTask2 keywords 回车换行 长度 超过最大值了");
                        throw new BusinessException("每个任务最大只支持" + maxSearchKeyValue + "个关键搜索");
                    }

                    int ansLen = paramReq.getAnswers().split("(\\r\\n|\\r|\\n|\\n\\r)").length;

                    if (ansLen > 5) {
                        logger.warn("saveTask2 answers 回车换行 长度 超过最大值了");
                        throw new BusinessException("每个任务最大只支持创建" + 5 + "个群组");
                    }
                }
            }
        }


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
            }
            break;
        }

        SysUser user = getCurrentUser();

        TasksWithBLOBs record = new TasksWithBLOBs();
        record.setTaskclass(paramReq.getTaskclass());
        record.setKeywords(paramReq.getKeywords());
        record.setContent(paramReq.getContent());
        record.setAnswers(paramReq.getAnswers());
        record.setMd5(Utility.getMd5(paramReq.getContent()));
        record.setRemarks(paramReq.getRemarks());
        record.setAction(paramReq.getAction());
        record.setDeadline(paramReq.getDeadline());
        record.setDelay(paramReq.getDelay());
        record.setGroupname(paramReq.getGroupname());
        record.setNolinks(paramReq.getNolinks());
        record.setMaxposts(paramReq.getMaxposts());
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
                logger.warn("saveTask 任务不存在  taskId = {}，username={}", paramReq.getTaskid(), user.getUserName());
                throw new XRobotException(ErrorCode.SYS_FAIL);
            }

            if (oldInfo.getState() == 1) {
                logger.warn("saveTask 任务表 执行中的任务不允许修改  taskId = {}", paramReq.getTaskid());
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

/*        if (oldInfo.getState() == 1) {
            logger.warn("delTask 任务表 执行中的任务不允许修改  id = {}", paramReq.getTaskid());
            throw new BusinessException("任务正在执行中，暂不允许删除");
        }*/


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

        List<TaskDevices> list = xrobotDao.taskDevicesAllList(paramReq.getTaskid(), user.getUserName());
        if (list == null || list.isEmpty()) {
            logger.warn("exeTask 该任务下没有对应的taskId={} 执行任务的终端", paramReq.getTaskid());
            throw new BusinessException("该任务下没有对应的 执行任务的终端");
        }

        Device device = null;
        for (TaskDevices taskDevices : list) {

            device = xrobotDao.getDeviceInfoByDeviceId(user.getUserName(), taskDevices.getDeviceid());

            checkDeviceByDeviceId(device, taskDevices.getDeviceid());

            // 在线
            if (robotServer.sessionIsActive(taskDevices.getDeviceid())) {

                String seesionStatusCode = robotServer.getSeesionStatus(taskDevices.getDeviceid());
                // 正常 发送新任务通知
                if (StringUtils.equals("100", seesionStatusCode)) {

                    // 有新任务通知
                    robotServer.sendTaskNotifyCommand(taskDevices.getDeviceid());

                } else {

                    // 根据设备编号 获取设备信息
                    logger.warn("exeTask 该任务下taskId={} 设备={} 连接状态不正常={}", paramReq.getTaskid() + taskDevices.getDeviceid(), Utility.getMonitorClientAppStatus(seesionStatusCode));
                    throw new BusinessException("该任务下设备" + taskDevices.getDeviceid() + "(" + device.getDevicesn() + ")状态异常：" + Utility.getMonitorClientAppStatus(seesionStatusCode));
                }

            } else {
                // 未在线
                logger.warn("exeTask 该任务下taskId={} 设备={} 未在线", paramReq.getTaskid() + taskDevices.getDeviceid());
                throw new BusinessException("该任务下设备" + taskDevices.getDeviceid() + "(" + device.getDevicesn() + ")状态异常：未在线");
            }
        }

        // 修改任务表为 1:执行中
        Tasks record = new Tasks();
        record.preUpdate(user);
        record.setId(oldInfo.getId());
        record.setCreateBy(user.getUserName());
        int num = xrobotDao.exeTask(record);

        if (num < 1) {
            logger.warn("exeTask 任务表 影响行数num = {}", num);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

        TaskDevices taskDevicesRecord = new TaskDevices();
        taskDevicesRecord.setState(0);
        taskDevicesRecord.preUpdate(user);
        taskDevicesRecord.setError(0);
        taskDevicesRecord.setErrormsg("");
        taskDevicesRecord.setBatch((oldInfo.getBatch().intValue() + 1));

        TaskDevicesExample example = new TaskDevicesExample();
        example.createCriteria().andDelFlagEqualTo(XRobotCode.DEL_0).andCreateByEqualTo(user.getUserName()).andTaskidEqualTo(paramReq.getTaskid());

        //xrobotDao.updateTaskDevices(taskDevicesRecord, example);

        xrobotDao.exeTaskDevices(taskDevicesRecord, example);


        // 插入任务设备日志表

        Map<String, Object> dbParams = new HashMap<>();
        dbParams.put("nowDate", record.getUpdateDate());
        dbParams.put("username", user.getUserName());
        dbParams.put("taskID", oldInfo.getTaskid());
        dbParams.put("list", list);
        dbParams.put("batch", (oldInfo.getBatch().intValue() + 1));
        // 任务执行终端表
        xrobotDao.insertTasksDevicesLog(dbParams);

    }

    @Override
    public List<TaskDevicesResp> taskDevicesList(TaskDevicesListReq paramReq) {
        logger.info("taskDevicesList paramReq = {}", paramReq);
        paramReq.setCurrentUser(getCurrentUser().getUserName());

        return xrobotDao.taskDevicesList(paramReq);


    }

    @Override
    public Map<String, Object> saveTaskInit(SaveTaskInitReq paramReq) {
        logger.info("saveTaskInit paramReq = {}", paramReq);


        Map<String, Object> resp = new HashMap<>();
        if (StringUtils.isEmpty(paramReq.getTaskid())) {

            // 所有的设备列表
            List<Device> devices = deviceAllList(0);
            resp.put("devices", devices);
            // 所有的分组信息
            List<DeviceGroup> deviceGroups = deviceGroupAllList();
            resp.put("deviceGroups", deviceGroups);
        } else {

            paramReq.setCurrentUser(getCurrentUser().getUserName());

            List<SaveTaskInitResp> list = xrobotDao.saveTaskInit(paramReq);

            TasksWithBLOBs taskInfo = xrobotDao.getTaskInfoById(paramReq.getTaskid(), getCurrentUser().getUserName());

            // 所有的分组信息
            List<DeviceGroup> deviceGroups = deviceGroupAllList();
            resp.put("deviceGroups", deviceGroups);

            resp.put("taskInfo", taskInfo);
            resp.put("initRespList", list);

        }

        return resp;

    }

    @Override
    public List<DeviceGroupMembersListResp> deviceGroupMembersAllList(DeviceGroupMembersListReq paramReq) {

        logger.info("deviceGroupMembersAllList req = {}", paramReq);

        paramReq.setCurrentUser(getCurrentUser().getUserName());
        return xrobotDao.deviceGroupMembersAllList(paramReq);
    }

    @Override
    public List<Device> monitorDeviceList(DeviceListReq paramReq) {
        logger.info("monitorDeviceList req = {}", paramReq);

        paramReq.setCurrentUser(getCurrentUser().getUserName());

        paramReq.setAllow(1);

        List<Device> list = xrobotDao.deviceAllListByUser(paramReq);

        if (list.isEmpty()) {
            return list;
        }

        for (Device xclient : list) {

            boolean isActive = robotServer.sessionIsActive(xclient.getDeviceid());
            xclient.setMonitorClientStatus(isActive);
            if (isActive) {
                String seesionStatusCode = robotServer.getSeesionStatus(xclient.getDeviceid());
                xclient.setMonitorClientAppStatusCode(seesionStatusCode);
                xclient.setMonitorClientAppStatus(Utility.getMonitorClientAppStatus(seesionStatusCode));
            } else {
                xclient.setMonitorClientAppStatusCode("未知");
                xclient.setMonitorClientAppStatus("未知状态");
            }
        }

        return list;
    }


    @Override
    public void serverStart(DelDeviceReq paramReq) {
        logger.info("serverStart paramReq = {}", paramReq);

        paramReq.validate();

        Device deviceInfo = xrobotDao.getDeviceInfoById(paramReq.getId(), getCurrentUser().getUserName());

        if (deviceInfo == null) {
            logger.warn("serverStart req = {},终端设备应用 不存在", paramReq);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

        // 设备已经是连接状态
        if (robotServer.sessionIsActive(deviceInfo.getDeviceid())) {

            String seesionStatusCode = robotServer.getSeesionStatus(deviceInfo.getDeviceid());

            // 客户端已暂停
            if (StringUtils.equals(seesionStatusCode, "200")) {
                // 启动客户端
                logger.info("serverStart sendStartCommand req deviceId = {}", deviceInfo.getDeviceid());
                boolean b = robotServer.sendStartCommand(deviceInfo.getDeviceid());
                logger.info("serverStart sendStartCommand response = {}", b);

            } else if (StringUtils.equals(seesionStatusCode, "100")) {
                // 正常
                logger.warn("终端设备连接已经是正常状态，不需要执行启动命令");
            } else {
                logger.warn("serverStart req = {},终端设备 状态={}，不能启动", seesionStatusCode);
                throw new BusinessException("终端设备 " + Utility.getMonitorClientAppStatus(seesionStatusCode) + ",不能启动");
            }


        } else {
            logger.warn("serverStart req = {},终端设备未连接", paramReq);
            throw new BusinessException("终端设备未连接！");
        }
    }

    @Override
    public void serverExit(DelDeviceReq paramReq) {
        logger.info("serverExit paramReq = {}", paramReq);

        paramReq.validate();

        Device deviceInfo = xrobotDao.getDeviceInfoById(paramReq.getId(), getCurrentUser().getUserName());

        if (deviceInfo == null) {
            logger.warn("serverExit req = {},终端设备应用 不存在", paramReq);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

        // 设备是连接状态, 可以执行退出操作
        if (robotServer.sessionIsActive(deviceInfo.getDeviceid())) {

            logger.info("serverExit sendExitCommand req deviceId = {}", deviceInfo.getDeviceid());
            // 退出客户端
            boolean b = robotServer.sendExitCommand(deviceInfo.getDeviceid());
            logger.info("serverExit sendExitCommand response = {}", b);

        } else {
            logger.warn("serverExit req = {},终端设备未连接", paramReq, deviceInfo.getState());
            throw new BusinessException("终端设备未连接！");
        }
    }

    @Override
    public void serverQuiet(DelDeviceReq paramReq) {
        logger.info("serverQuiet paramReq = {}", paramReq);

        paramReq.validate();

        Device deviceInfo = xrobotDao.getDeviceInfoById(paramReq.getId(), getCurrentUser().getUserName());

        if (deviceInfo == null) {
            logger.warn("serverQuiet req = {},终端设备应用 不存在", paramReq);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

        // 设备是连接状态, 可以执行退出操作
        if (robotServer.sessionIsActive(deviceInfo.getDeviceid())) {

            String seesionStatusCode = robotServer.getSeesionStatus(deviceInfo.getDeviceid());

            // 客户端已暂停
            if (StringUtils.equals(seesionStatusCode, "200")) {
                logger.warn("终端设备连接已经暂停状态，不需要执行暂停命令");
            } else {
                logger.info("serverQuiet sendQuietCommand req deviceId = {}", deviceInfo.getDeviceid());
                boolean b = robotServer.sendQuietCommand(deviceInfo.getDeviceid());
                logger.info("serverQuiet sendQuietCommand response = {}", b);
            }

        } else {
            logger.warn("serverQuiet req = {},终端设备未连接", paramReq, deviceInfo.getState());
            throw new BusinessException("终端设备未连接！");
        }
    }

    @Override
    public PageResult taskResultList(ExeResultReq paramReq) {
        logger.info("taskResultList paramReq = {}", paramReq);

        paramReq.validate();

        paramReq.setCurrentUser(getCurrentUser().getUserName());

/*        1. 搜索加群消息任务
        2. 首页链接消息任务
        3. 创建群组发帖任务
        4. 搜索加群评论任务
        5. 首页帖子评论任务*/


        // 1发消息加群结果表;2发消息执行结果表;3评论加群结果表;4评论执行结果表;5创建群组发帖任务执行结果表
        if (paramReq.getCode() == 1) {
            // 发消息加群结果表(FB)-pushJoinGroups
            List<PushJoinGroups> list = xrobotDao.pushJoinGroupsList(paramReq);

            PageInfo<PushJoinGroups> pageInfo = new PageInfo<>(list);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            return pageResult;
        } else if (paramReq.getCode() == 2) {

            // 发消息执行结果表-pushMessages
            List<PushMessages> list = xrobotDao.pushMessagesList(paramReq);

            PageInfo<PushMessages> pageInfo = new PageInfo<>(list);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            return pageResult;

        } else if (paramReq.getCode() == 3) {
            // 评论加群结果表(FB)-commentJoinGroups
            List<CommentJoinGroups> list = xrobotDao.commentJoinGroupsList(paramReq);

            PageInfo<CommentJoinGroups> pageInfo = new PageInfo<>(list);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            return pageResult;

        } else if (paramReq.getCode() == 4) {
            // 评论执行结果表-comments
            List<Comments> list = xrobotDao.commentsList(paramReq);

            PageInfo<Comments> pageInfo = new PageInfo<>(list);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            return pageResult;

        } else if (paramReq.getCode() == 5) {
            // 创建群组发帖任务执行结果表-createGroups
            List<CreateGroups> list = xrobotDao.createGroupsList(paramReq);

            PageInfo<CreateGroups> pageInfo = new PageInfo<>(list);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            return pageResult;

        } else if (paramReq.getCode() == 6) {
            // 关键字加群统计
            List<SummaryJoinGroups> list = xrobotDao.summaryJoinGroupsList(paramReq);

            PageInfo<SummaryJoinGroups> pageInfo = new PageInfo<>(list);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            return pageResult;

        } else {
            logger.warn("taskResultList code = {} 不正确", paramReq.getCode());
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }


    }

    @Override
    public void deviceResetAllowState(DeviceResetAllowStateReq paramReq) {
        logger.info("deviceResetAllowState paramReq = {}", paramReq);

        paramReq.validate();
        SysUser user = getCurrentUser();
        Device oldInfo = xrobotDao.getDeviceInfoById(paramReq.getId(), user.getUserName());

        if (oldInfo == null) {
            logger.warn("deviceResetAllowState req = {},终端设备应用 不存在", paramReq);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

        if (robotServer.sessionIsActive(oldInfo.getDeviceid())) {
            logger.warn("deviceResetAllowState req = {},终端设备应用编号 连接的状态 不允许暂停使用", paramReq);
            throw new BusinessException("设备已登录，无法暂停使用！");
        }

        DeviceExample example = new DeviceExample();
        example.createCriteria().andIdEqualTo(paramReq.getId()).andCreateByEqualTo(user.getUserName());

        Device record = new Device();
        record.setAllow(paramReq.getAllow());
        record.preUpdate(user);

        xrobotDao.updateDevice(record, example);
    }

    @Override
    public List<Device> monitorAdminDeviceList(DeviceListReq paramReq) {
        logger.info("monitorAdminDeviceList req = {}", paramReq);

        paramReq.setAllow(1);

        List<Device> list = xrobotDao.deviceAllListByUser(paramReq);

        if (list.isEmpty()) {
            return list;
        }

        for (Device xclient : list) {

            boolean isActive = robotServer.sessionIsActive(xclient.getDeviceid());
            xclient.setMonitorClientStatus(isActive);
            if (isActive) {
                String seesionStatusCode = robotServer.getSeesionStatus(xclient.getDeviceid());
                xclient.setMonitorClientAppStatusCode(seesionStatusCode);
                xclient.setMonitorClientAppStatus(Utility.getMonitorClientAppStatus(seesionStatusCode));
            } else {
                xclient.setMonitorClientAppStatusCode("未知");
                xclient.setMonitorClientAppStatus("未知状态");
            }
        }

        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, value = "phoenixTransactionManager")
    public void appUpload(AppUploadReq paramReq, HttpServletRequest request) {
        logger.info("appUpload paramReq = {}", paramReq);

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
                if (fileSuffix == null || !app_suffix.contains(fileSuffix)) {
                    logger.warn("appUpload  上传的文件格式不支持 = {}", originalFilename);
                    throw new XRobotException(ErrorCode.ERROR_CODE_21);
                }

                String filePath = baseAppUploadPath + originalFilename;

                File dest = new File(filePath);

                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }

                try {
                    file.transferTo(dest);  // 文件上传到 服务器
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("appUpload 文件上传失败: ex = {}", e);
                }

                newFilePath = filePath;
            }
            break;
        }

        AppVersion record = new AppVersion();
        record.setOsname(paramReq.getOsname());
        record.setContext(paramReq.getContext());
        record.setTitle(paramReq.getTitle());
        record.setForceflag(paramReq.getForceflag());
        record.setDownloadurl(newFilePath);
        record.preInsert(getCurrentUser());
        record.setAppversion(paramReq.getAppversion());

        xrobotDao.deleteAppVersion();
        xrobotDao.insertAppVersion(record);

    }

    @Override
    public AppVersion getAppUrl() {
        return xrobotDao.getAppVersion();
    }

    @Override
    public PageResult taskExeResultList(TaskExeResultReq paramReq) {
        logger.info("taskExeResultList paramReq = {}", paramReq);

        paramReq.setCurrentUser(getCurrentUser().getUserName());


        List<TaskExeResultResp> list = xrobotDao.taskExeResult(paramReq);

        PageInfo<TaskExeResultResp> pageInfo = new PageInfo<>(list);
        PageResult pageResult = PageUtils.getPageResult(pageInfo);
        return pageResult;
    }

    @Override
    public String exportUserNumberList() {


        List<String> userNumberList = xrobotDao.exportUserNumberList();

        if (userNumberList.isEmpty()) {
            throw new BusinessException("数据为空");
        }


        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(new FileInputStream(exportUsernameModel)), 1000);
            Sheet sheet0 = workbook.getSheetAt(0);

            int indexRowNum = 1;
            Row row = null;
            Cell cell = null;


            for (String userNumber : userNumberList) {
                row = sheet0.createRow(indexRowNum);

                cell = row.createCell(0);
                cell.setCellValue(userNumber);

                indexRowNum++;
            }


            String fileName = System.currentTimeMillis() + "_群员手机号.xlsx";

            String filePath = outExcelPath + fileName;

            File dest = new File(filePath);

            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            FileOutputStream out = new FileOutputStream(filePath); // 创建文件输出流
            workbook.write(out);
            out.flush();
            out.close();

            return fileName;

        } catch (Exception ex) {
            logger.error("exportUserNumberList 下载excel 未知错误");
            ex.printStackTrace();
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

    }

    @Override
    public PageResult userNumberList(UserNumberListReq paramReq) {
        logger.info("userNumberList req = {}", paramReq);

        paramReq.setCurrentUser(getCurrentUser().getUserName());

        List<Wsusernumbers> list = xrobotDao.userNumberList(paramReq);

        PageInfo<Wsusernumbers> pageInfo = new PageInfo<>(list);
        PageResult pageResult = PageUtils.getPageResult(pageInfo);

        return pageResult;
    }

    @Override
    public Map<String, Integer> userNumberCount() {
        Map<String, Integer> resp = new HashMap<>();


        Integer allUserNumberCount = xrobotDao.getAllUserNumberCount();

        resp.put("allUserNumberCount", allUserNumberCount);

        Integer userNumberCount = xrobotDao.userNumberCount();

        resp.put("userNumberCount", userNumberCount);

        return resp;
    }

    @Override
    public void userNumberClear() {


        xrobotDao.userNumberClear();
        logger.info("用户={} 执行了清理数据", getCurrentUser().getUserName());
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

    private void checkDeviceByDeviceId(Device device, String devicdId) {
        if (device == null) {
            logger.warn("checkDeviceByDeviceId deviceID={} 对应的设备不存在", devicdId);
            throw new BusinessException("该任务下设备" + devicdId + "对应设备不存在");

        }

        if (device.getAllow() == 0) {
            logger.warn("checkDeviceByDeviceId deviceID={} 对应的设备 被禁用", devicdId);
            throw new BusinessException("该任务下设备" + devicdId + "(" + device.getDevicesn() + ")账号状态异常：账号被禁用");
        }
    }
}
