package com.fairyland.xrobot.modular.xrobot.controller;

import com.alibaba.fastjson.JSON;
import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.MessageUtils;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.framework.web.domain.AjaxResult;
import com.fairyland.xrobot.modular.xrobot.domain.*;
import com.fairyland.xrobot.modular.xrobot.domain.req.*;
import com.fairyland.xrobot.modular.xrobot.domain.resp.*;
import com.fairyland.xrobot.modular.xrobot.exception.BusinessException;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import com.fairyland.xrobot.modular.xrobot.service.XrobotService;
import com.fairyland.xrobot.modular.xrobot.utils.QrCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: fairyland->XrobotController
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 11:29
 **/
@RestController
@RequestMapping(value = "/xrobot")
public class XrobotController {

    private Logger logger = LoggerFactory.getLogger(XrobotController.class);

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private XrobotService xrobotService;


    @Value("${qrcodew}")
    private int qrcodew;

    @Value("${qrcodeh}")
    private int qrcodeh;

    @Value("${netty.host}")
    private String host;

    @Value("${netty.port}")
    private int port;

    /**
     * @Description: 终端设备列表 （带分页的）
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 11:39
     */
    @RequestMapping(value = "/deviceList")
    @PreAuthorize("@ss.hasPermi('device:list')")
    public AjaxResult deviceList(@RequestBody DeviceListReq paramReq) {

        AjaxResult webResponse = null;
        try {

            PageResult resp = xrobotService.deviceList(paramReq);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 终端设备列表 （不分页的）
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 11:39
     */
    @RequestMapping(value = "/deviceAllList")
    @PreAuthorize("@ss.hasPermi('device:list')")
    public AjaxResult deviceAllList() {

        AjaxResult webResponse = null;
        try {

            List<Device> resp = xrobotService.deviceAllList(null);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 终端设备监控中心（不分页）
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/5/26 16:07
     */
    @RequestMapping(value = "/monitorDeviceList")
    @PreAuthorize("@ss.hasPermi('device:monitor')")
    public AjaxResult monitorDeviceList(@RequestBody DeviceListReq paramReq) {

        AjaxResult webResponse = null;
        try {

            List<Device> list = xrobotService.monitorDeviceList(paramReq);

            webResponse = AjaxResult.success(list);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 管理员终端设备监控中心（不分页）
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/5/26 16:07
     */
    @RequestMapping(value = "/monitorAdminDeviceList")
    @PreAuthorize("@ss.hasPermi('device:monitor')")
    public AjaxResult monitorAdminDeviceList(@RequestBody DeviceListReq paramReq) {

        AjaxResult webResponse = null;
        try {

            List<Device> list = xrobotService.monitorAdminDeviceList(paramReq);

            webResponse = AjaxResult.success(list);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 根据唯一标识 获得 终端设备信息
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:59
     */
    @RequestMapping(value = "/getDeviceInfoById")
    @PreAuthorize("@ss.hasPermi('device:list')")
    public AjaxResult getDeviceInfoById(@RequestBody DelDeviceReq paramReq) {

        AjaxResult webResponse = null;
        try {

            Device resp = xrobotService.getDeviceInfoById(paramReq);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 保存 终端设备信息
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 12:20
     */
    @RequestMapping(value = "/saveDevice")
    @PreAuthorize("@ss.hasPermi('device:edit')")
    public AjaxResult saveDevice(@RequestBody SaveDeviceReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.saveDevice(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 重置设备状态(恢复为可用 未连接 状态)
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:45
     */
    @RequestMapping(value = "/resetDeviceState")
    @PreAuthorize("@ss.hasPermi('device:edit')")
    public AjaxResult resetDeviceState(@RequestBody ResetDeviceStateReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.resetDeviceState(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 删除 终端设备信息
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:03
     */
    @RequestMapping(value = "/delDevice")
    @PreAuthorize("@ss.hasPermi('device:del')")
    public AjaxResult delDevice(@RequestBody DelDeviceReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.delDevice(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 设备 暂停使用，恢复使用
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:03
     */
    @RequestMapping(value = "/deviceResetAllowState")
    @PreAuthorize("@ss.hasPermi('device:edit')")
    public AjaxResult deviceResetAllowState(@RequestBody DeviceResetAllowStateReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.deviceResetAllowState(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    // == 终端设备分组表============================


    /**
     * @Description: 终端设备分组 列表（带分页）
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 16:11
     */
    @RequestMapping(value = "/deviceGroupList")
    @PreAuthorize("@ss.hasPermi('deviceGroup:list')")
    public AjaxResult deviceGroupList(@RequestBody DeviceGroupListReq paramReq) {

        AjaxResult webResponse = null;
        try {

            PageResult resp = xrobotService.deviceGroupList(paramReq);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 终端设备分组 列表（不分页）
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 16:11
     */
    @RequestMapping(value = "/deviceGroupAllList")
    @PreAuthorize("@ss.hasPermi('deviceGroup:list')")
    public AjaxResult deviceGroupAllList() {

        AjaxResult webResponse = null;
        try {

            List<DeviceGroup> resp = xrobotService.deviceGroupAllList();

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 根据唯一标识 获得 终端设备分组信息
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 16:38
     */
    @RequestMapping(value = "/getDeviceGroupInfoById")
    @PreAuthorize("@ss.hasPermi('deviceGroup:list')")
    public AjaxResult getDeviceGroupInfoById(@RequestBody DelDeviceGroupReq paramReq) {

        AjaxResult webResponse = null;
        try {

            DeviceGroup resp = xrobotService.getDeviceGroupInfoById(paramReq);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }

    /**
     * @Description: 保存 终端设备分组信息
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 12:20
     */
    @RequestMapping(value = "/saveDeviceGroup")
    @PreAuthorize("@ss.hasPermi('deviceGroup:edit')")
    public AjaxResult saveDeviceGroup(@RequestBody SaveDeviceGroupReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.saveDeviceGroup(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 删除 终端设备分组信息
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:03
     */
    @RequestMapping(value = "/delDeviceGroup")
    @PreAuthorize("@ss.hasPermi('deviceGroup:del')")
    public AjaxResult delDeviceGroup(@RequestBody DelDeviceGroupReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.delDeviceGroup(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    // == 终端设备分组成员表============================


    /**
     * @Description: 终端设备分组成员 列表（带分页）
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 16:11
     */
    @RequestMapping(value = "/deviceGroupMembersList")
    @PreAuthorize("@ss.hasPermi('deviceGroupMembers:list')")
    public AjaxResult deviceGroupMembersList(@RequestBody DeviceGroupMembersListReq paramReq) {

        AjaxResult webResponse = null;
        try {

            PageResult resp = xrobotService.deviceGroupMembersList(paramReq);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 终端设备分组成员 列表不分页
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 16:11
     */
    @RequestMapping(value = "/deviceGroupMembersAllList")
    @PreAuthorize("@ss.hasPermi('deviceGroupMembers:list')")
    public AjaxResult deviceGroupMembersAllList(@RequestBody DeviceGroupMembersListReq paramReq) {

        AjaxResult webResponse = null;
        try {

            List<DeviceGroupMembersListResp> resp = xrobotService.deviceGroupMembersAllList(paramReq);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 移除 终端设备分组成员
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 17:41
     */
    @RequestMapping(value = "/delDeviceGroupMembers")
    @PreAuthorize("@ss.hasPermi('deviceGroupMembers:del')")
    public AjaxResult delDeviceGroupMembers(@RequestBody DelDeviceGroupMembersReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.delDeviceGroupMembers(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 保存终端设备分组成员 初始化
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 16:11
     */
    @RequestMapping(value = "/saveDeviceGroupMembersInit")
    @PreAuthorize("@ss.hasPermi('deviceGroupMembers:edit')")
    public AjaxResult saveDeviceGroupMembersInit(@RequestBody DeviceGroupMembersInitReq paramReq) {

        AjaxResult webResponse = null;
        try {

            List<DeviceGroupMembersInitResp> list = xrobotService.saveDeviceGroupMembersInit(paramReq);

            webResponse = AjaxResult.success(list);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 终端设备分组 添加成员
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 17:41
     */
    @RequestMapping(value = "/saveDeviceGroupMembers")
    @PreAuthorize("@ss.hasPermi('deviceGroupMembers:edit')")
    public AjaxResult saveDeviceGroupMembers(@RequestBody DeviceGroupMembersReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.saveDeviceGroupMembers(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 二维码生成工具
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:59
     */
    @RequestMapping(value = "/fileToQRCode")
    @PreAuthorize("@ss.hasPermi('fileToQRCode:save')")
    public AjaxResult fileToQRCode(@RequestBody FileToQRCodeReq paramReq) {

        AjaxResult webResponse = null;
        try {

            paramReq.validate();

            String qrcodestr = QrCodeUtils.creatRrCode(paramReq.getContext(), qrcodew, qrcodeh);

            webResponse = AjaxResult.success(qrcodestr);

        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 根据唯一标识 生成json 字符串
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:59
     */
    @RequestMapping(value = "/getQRCodeJsonById")
    @PreAuthorize("@ss.hasPermi('device:list')")
    public AjaxResult getQRCodeJsonById(@RequestBody DelDeviceReq paramReq) {

        AjaxResult webResponse = null;
        try {

            QRCodeResp resp = xrobotService.getQRCodeJsonById(paramReq);

            resp.setHost(host);
            resp.setPort(port);

            String contents = JSON.toJSONString(resp);//关键

            String qrcodestr = QrCodeUtils.creatRrCode(contents, qrcodew, qrcodeh);

            Map<String, Object> realData = new HashMap<>();
            realData.put("jsonData", resp);
            realData.put("qrcode", qrcodestr);

            webResponse = AjaxResult.success(realData);

        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    // == 系统设置============================

    /**
     * @Description: 系统字典信息 列表（不分页）
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 16:11
     */
    @RequestMapping(value = "/dictList")
    @PreAuthorize("@ss.hasPermi('dict:list')")
    public AjaxResult dictList() {

        AjaxResult webResponse = null;
        try {

            List<Dict> resp = xrobotService.dictList();

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 修改 系统字典 信息
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 12:20
     */
    @RequestMapping(value = "/saveDict")
    @PreAuthorize("@ss.hasPermi('dict:edit')")
    public AjaxResult saveDict(@RequestBody SaveDictReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.saveDict(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 任务分类 列表（不分页）
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 16:11
     */
    @RequestMapping(value = "/taskDictList")
    @PreAuthorize("@ss.hasPermi('taskDict:list')")
    public AjaxResult taskDictList() {

        AjaxResult webResponse = null;
        try {

            List<TaskDict> resp = xrobotService.taskDictList();

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 任务列表(带分页)
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/4 17:21
     */
    @RequestMapping(value = "/taskList")
    @PreAuthorize("@ss.hasPermi('task:list')")
    public AjaxResult taskList(@RequestBody TaskListReq paramReq) {

        AjaxResult webResponse = null;
        try {

            PageResult resp = xrobotService.taskList(paramReq);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 任务执行终端列表(不分页了)
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/4 22:16
     */
    @RequestMapping(value = "/taskDevicesList")
    @PreAuthorize("@ss.hasPermi('taskDevices:list')")
    public AjaxResult taskDevicesList(@RequestBody TaskDevicesListReq paramReq) {

        AjaxResult webResponse = null;
        try {

            List<TaskDevicesResp> list = xrobotService.taskDevicesList(paramReq);

            webResponse = AjaxResult.success(list);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 保存 任务表信息 初始化
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:59
     */
    @RequestMapping(value = "/saveTaskInit")
    @PreAuthorize("@ss.hasPermi('task:edit')")
    public AjaxResult saveTaskInit(@RequestBody SaveTaskInitReq paramReq) {

        AjaxResult webResponse = null;
        try {

            Map<String, Object> resp = xrobotService.saveTaskInit(paramReq);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 保存 任务表信息
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 12:20
     */
    @RequestMapping(value = "/saveTask")
    @PreAuthorize("@ss.hasPermi('task:edit')")
    public AjaxResult saveTask(SaveTaskReq paramReq, HttpServletRequest request) {

        AjaxResult webResponse = null;
        try {

            xrobotService.saveTask(paramReq, request);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 删除 任务表信息
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:03
     */
    @RequestMapping(value = "/delTask")
    @PreAuthorize("@ss.hasPermi('task:del')")
    public AjaxResult delTask(@RequestBody DelTaskReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.delTask(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 执行或重新执行 任务
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:03
     */
    @RequestMapping(value = "/exeTask")
    @PreAuthorize("@ss.hasPermi('task:exe')")
    public AjaxResult exeTask(@RequestBody ExeTaskReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.exeTask(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    // ============操作设备========================

    /**
     * @Description: 启动客户端
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:59
     */
    @RequestMapping(value = "/serverStart")
    @PreAuthorize("@ss.hasPermi('device:command')")
    public AjaxResult serverStart(@RequestBody DelDeviceReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.serverStart(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 退出客户端
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:59
     */
    @RequestMapping(value = "/serverExit")
    @PreAuthorize("@ss.hasPermi('device:command')")
    public AjaxResult serverExit(@RequestBody DelDeviceReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.serverExit(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 暂停操作
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/2 15:59
     */
    @RequestMapping(value = "/serverQuiet")
    @PreAuthorize("@ss.hasPermi('device:command')")
    public AjaxResult serverQuiet(@RequestBody DelDeviceReq paramReq) {

        AjaxResult webResponse = null;
        try {

            xrobotService.serverQuiet(paramReq);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(998, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    // =================任务 执行 结果表==============================

    /**
     * @Description: 任务执行结果列表(带分页)
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/4 22:16
     */
    @RequestMapping(value = "/taskResultList")
    @PreAuthorize("@ss.hasPermi('taskResult:list')")
    public AjaxResult taskResultList(@RequestBody ExeResultReq paramReq) {

        AjaxResult webResponse = null;
        try {

            PageResult resp = xrobotService.taskResultList(paramReq);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: app 上传
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/10 1:24
     */
    @RequestMapping(value = "/appUpload")
    @PreAuthorize("@ss.hasPermi('app:upload')")
    public AjaxResult appUpload(AppUploadReq paramReq, HttpServletRequest request) {

        AjaxResult webResponse = null;
        try {

            xrobotService.appUpload(paramReq, request);

            webResponse = AjaxResult.success();
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    @RequestMapping(value = "/getAppUrl")
    @PreAuthorize("@ss.hasPermi('app:url')")
    public AjaxResult getAppUrl() {

        AjaxResult webResponse = null;
        try {

            AppVersion appVersion = xrobotService.getAppUrl();

            if (appVersion != null && StringUtils.isNotEmpty(appVersion.getVoDownloadurl())) {
                String qrcodestr = QrCodeUtils.creatRrCode(appVersion.getVoDownloadurl(), qrcodew, qrcodeh);

                appVersion.setQrcode(qrcodestr);
            }

            webResponse = AjaxResult.success(appVersion);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


    /**
     * @Description: 任务执行状态结果表
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/10/17 20:27
     */
    @RequestMapping(value = "/taskExeResultList")
    @PreAuthorize("@ss.hasPermi('taskExeResult:list')")
    public AjaxResult taskExeResultList(@RequestBody TaskExeResultReq paramReq) {

        AjaxResult webResponse = null;
        try {

            PageResult resp = xrobotService.taskExeResultList(paramReq);

            webResponse = AjaxResult.success(resp);
        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (BusinessException ex) {
            logger.warn("BusinessException={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, ex.getTipsMessage());
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


}
