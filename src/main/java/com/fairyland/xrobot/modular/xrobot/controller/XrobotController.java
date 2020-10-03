package com.fairyland.xrobot.modular.xrobot.controller;

import com.alibaba.fastjson.JSON;
import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.MessageUtils;
import com.fairyland.xrobot.framework.web.domain.AjaxResult;
import com.fairyland.xrobot.modular.xrobot.domain.Device;
import com.fairyland.xrobot.modular.xrobot.domain.DeviceGroup;
import com.fairyland.xrobot.modular.xrobot.domain.req.*;
import com.fairyland.xrobot.modular.xrobot.domain.resp.DeviceGroupMembersInitResp;
import com.fairyland.xrobot.modular.xrobot.domain.resp.PageResult;
import com.fairyland.xrobot.modular.xrobot.domain.resp.QRCodeResp;
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
     * @Description: 终端设备列表 （不分页的） 暂时未用到
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

            List<Device> resp = xrobotService.deviceAllList();

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
     * @Description: 重置设备状态(回复为可用 未连接 状态)
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

            String contents = JSON.toJSONString(resp);//关键

            String qrcodestr = QrCodeUtils.creatRrCode(contents, qrcodew, qrcodeh);

            Map<String, Object> realData = new HashMap<>();
            realData.put("jsonData", resp);
            realData.put("qrcode", qrcodestr);

            webResponse = AjaxResult.success(realData);

        } catch (XRobotException ex) {
            logger.warn("XRobotException={}", ex);
            webResponse = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (Exception ex) {
            logger.error("Exception={}", ex);
            webResponse = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }

        return webResponse;
    }


}
