package com.fairyland.xrobot.modular.xrobot.service.impl;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.ServletUtils;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.framework.security.LoginUser;
import com.fairyland.xrobot.framework.security.service.TokenService;
import com.fairyland.xrobot.modular.system.domain.SysUser;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import com.fairyland.xrobot.modular.xrobot.service.BaseService;
import com.fairyland.xrobot.modular.xrobot.utils.base62.NumberUtil;
import com.fairyland.xrobot.modular.xrobot.utils.base62.SnowflakeIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @program: fairyland->BaseServiceImpl
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-24 19:00
 **/
@Service
public class BaseServiceImpl implements BaseService {

    private Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Value("${workerId}")
    private long workerId;

    @Value("${datacenterId}")
    private long datacenterId;

    @Autowired
    private TokenService tokenService;

    @Override
    public SysUser getCurrentUser() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();

        if (user == null || StringUtils.isEmpty(user.getUserName())) {
            logger.warn("getCurrentUser user = {}", user);

            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

        return user;
    }


    public String getSerializeVal() {

        try {
            SnowflakeIdWorker idWorker = SnowflakeIdWorker.getInstance();
            idWorker.setWorker(workerId, datacenterId);

            long id = idWorker.nextId();

            String serialize = NumberUtil.encode58(id, 0);

            return serialize;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("生成唯一标识 错误1{}", ex);
            throw new XRobotException(ErrorCode.SYS_FAIL);
        }

    }
}
