package com.fairyland.xrobot.modular.xrobot.service.impl;

import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClinetLoginReq;
import com.fairyland.xrobot.modular.xrobot.dao.XrobotDao;
import com.fairyland.xrobot.modular.xrobot.domain.Device;
import com.fairyland.xrobot.modular.xrobot.service.AutoxitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: fairyland->AutoxitServiceImpl
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-03 09:24
 **/
@Service
public class AutoxitServiceImpl implements AutoxitService {



    @Autowired
    private XrobotDao xrobotDao;

    @Override
    public Device checkClinetLogin(ClinetLoginReq paramReq) {


        return xrobotDao.checkClinetLogin(paramReq);
    }
}
