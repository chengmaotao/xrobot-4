package com.fairyland.xrobot.modular.xrobot.service.impl;

import com.fairyland.xrobot.framework.redis.RedisCache;
import com.fairyland.xrobot.modular.xrobot.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: fairyland->RedisServiceImpl
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-22 18:29
 **/
@Service
public class RedisServiceImpl implements RedisService {

    private final String FLUCRM_PRE_ = "xrobot:";

    @Autowired
    private RedisCache redisCache;

}
