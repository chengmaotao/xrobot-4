package com.fairyland.xrobot.modular.xrobot.service.impl;

import com.fairyland.xrobot.modular.system.domain.SysUser;
import com.fairyland.xrobot.modular.system.mapper.SysUserMapper;
import com.fairyland.xrobot.modular.xrobot.domain.req.XrobotUserListReq;
import com.fairyland.xrobot.modular.xrobot.service.UserService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SysUserMapper userMapper;


    @Override
    public List<SysUser> userList(XrobotUserListReq params) {
        int pageNum = params.getPageNum();
        int pageSize = params.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectXrobotUserList(params);
    }
}
