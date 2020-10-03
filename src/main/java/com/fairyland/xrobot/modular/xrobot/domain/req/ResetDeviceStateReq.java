package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->ResetDeviceStateReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 15:47
 **/
public class ResetDeviceStateReq {

    private Logger logger = LoggerFactory.getLogger(ResetDeviceStateReq.class);

    private Long id; //  唯一标识

    private String deviceid;  // 终端设备记录唯一ID

    private String phone; // 终端设备手机号码

    private String account;  // 目标APP账号(facebook)

    private String password; // 目标APP密码(facebook)

    private Integer login; // 登录方式(0:账号密码 1:手机号短信验证码)

    private String account1; // 目标APP1账号(wsup)

    private String password1; // 目标APP1密码

    private Integer login1; // 登录方式(0:账号密码 1:手机号短信验证码)

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void validate() {

        if (id == null) {
            logger.warn("ResetDeviceStateReq 终端设备唯一标识 id = {} 不正确", id);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (StringUtils.isEmpty(deviceid)) {
            logger.warn("ResetDeviceStateReq 终端设备应用编号 deviceid = {} 不正确", deviceid);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (StringUtils.isEmpty(phone)) {
            logger.warn("ResetDeviceStateReq 终端设备手机号码 phone = {} 不正确 超出长度限制16", phone);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (StringUtils.isEmpty(account) || account.length() > 32) {
            logger.warn("ResetDeviceStateReq 目标APP账号(facebook) account = {} 不正确 超出长度限制32", account);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (StringUtils.isEmpty(password) || password.length() > 32) {
            logger.warn("ResetDeviceStateReq 目标APP密码(facebook) password = {} 不正确 超出长度限制32", password);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (login == null || (login != 0 && login != 1)) {
            logger.warn("ResetDeviceStateReq facebook 登录方式(0:账号密码 1:手机号短信验证码) login = {} 不正确", login);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }


        if (StringUtils.isEmpty(account1) || account1.length() > 32) {
            logger.warn("SaveDeviceReq 目标APP1账号(wsup) account1 = {} 不正确 超出长度限制32", account1);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (StringUtils.isEmpty(password1) || password1.length() > 32) {
            logger.warn("ResetDeviceStateReq 目标APP密码(wsup) password1 = {} 不正确 超出长度限制32", password1);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (login1 == null || (login1 != 0 && login1 != 1)) {
            logger.warn("ResetDeviceStateReq wsup 登录方式(0:账号密码 1:手机号短信验证码) login1 = {} 不正确", login1);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

    }


    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getAccount1() {
        return account1;
    }

    public void setAccount1(String account1) {
        this.account1 = account1;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public Integer getLogin1() {
        return login1;
    }

    public void setLogin1(Integer login1) {
        this.login1 = login1;
    }

    @Override
    public String toString() {
        return "ResetDeviceStateReq{" +
                "id=" + id +
                ", deviceid='" + deviceid + '\'' +
                ", phone='" + phone + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", login=" + login +
                ", account1='" + account1 + '\'' +
                ", password1='" + password1 + '\'' +
                ", login1=" + login1 +
                '}';
    }
}
