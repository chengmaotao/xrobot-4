package com.fairyland.xrobot.modular.xrobot.service;

import com.fairyland.xrobot.modular.system.domain.SysUser;
import com.fairyland.xrobot.modular.xrobot.domain.req.XrobotUserListReq;

import java.util.List;

public interface UserService {


    List<SysUser> userList(XrobotUserListReq params);
}
