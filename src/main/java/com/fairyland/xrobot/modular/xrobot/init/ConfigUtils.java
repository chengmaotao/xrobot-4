package com.fairyland.xrobot.modular.xrobot.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: fairyland->ConfigUtils
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-28 19:59
 **/
@Component
@Slf4j
public class ConfigUtils implements InitializingBean {


    @Value("${server.host}")
    private String serverHost;

    public static String hostAddress;

    @Override
    public void afterPropertiesSet() throws Exception {
        hostAddress = serverHost;
    }



}
