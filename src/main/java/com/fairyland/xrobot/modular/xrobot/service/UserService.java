package com.fairyland.xrobot.modular.xrobot.service;

import com.alibaba.fastjson.JSONObject;
import com.fairyland.xrobot.modular.system.domain.SysUser;
import com.fairyland.xrobot.modular.xrobot.domain.req.XrobotUserListReq;

import java.util.List;

public interface UserService {


    JSONObject getCaptchaParamsJson();

    void checkCaptcha(JSONObject paramsJson);

    List<SysUser> userList(XrobotUserListReq params);
}
