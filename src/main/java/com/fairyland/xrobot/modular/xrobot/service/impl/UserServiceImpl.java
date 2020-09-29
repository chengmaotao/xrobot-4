package com.fairyland.xrobot.modular.xrobot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.modular.system.domain.SysUser;
import com.fairyland.xrobot.modular.system.mapper.SysUserMapper;
import com.fairyland.xrobot.modular.xrobot.domain.req.XrobotUserListReq;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import com.fairyland.xrobot.modular.xrobot.service.UserService;
import com.github.pagehelper.PageHelper;
import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: fairyland->UserServiceImpl
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-14 15:56
 **/
@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${tengxun.secretId}")
    private String secretId;

    @Value("${tengxun.secretKey}")
    private String secretKey;

    @Value("${tengxun.CaptchaAppId}")
    private String captchaAppId;

    @Value("${tengxun.AppSecretKey}")
    private String appSecretKey;

    @Autowired
    private SysUserMapper userMapper;


    @Override
    public void checkCaptcha(JSONObject paramsJson) {


        try {

            Credential cred = new Credential(secretId, secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("captcha.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            CaptchaClient client = new CaptchaClient(cred, "", clientProfile);


            String params = paramsJson.toJSONString();
            DescribeCaptchaResultRequest req = DescribeCaptchaResultRequest.fromJsonString(params, DescribeCaptchaResultRequest.class);

            DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);

            // 响应结果
            Long captchaCode = resp.getCaptchaCode();

            // 校验失败
            if (captchaCode.compareTo(1L) != 0) {
                logger.info("腾讯验证码 校验未通过={}", DescribeCaptchaResultRequest.toJsonString(resp));
                throw new XRobotException(ErrorCode.ERROR_CODE_6);
            }

            //System.out.println(DescribeCaptchaResultRequest.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            logger.error("checkCaptcha TencentCloudSDKException 校验腾讯验证码 失败 ={}", e.getMessage());
            e.printStackTrace();
            throw new XRobotException(ErrorCode.ERROR_CODE_6);
        }

    }


    @Override
    public JSONObject getCaptchaParamsJson() {
        JSONObject paramsJson = new JSONObject();

        paramsJson.put("Action", "DescribeCaptchaResult");
        paramsJson.put("Version", "2019-07-22");
        paramsJson.put("CaptchaType", 9);
        paramsJson.put("CaptchaAppId", captchaAppId);
        paramsJson.put("AppSecretKey", appSecretKey);

        return paramsJson;

    }

    @Override
    public List<SysUser> userList(XrobotUserListReq params) {
        int pageNum = params.getPageNum();
        int pageSize = params.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectXrobotUserList(params);
    }
}
