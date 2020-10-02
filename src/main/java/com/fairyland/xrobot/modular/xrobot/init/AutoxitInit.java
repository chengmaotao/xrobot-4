package com.fairyland.xrobot.modular.xrobot.init;


import com.fairyland.xrobot.modular.xrobot.autoxit.server.LinkerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: fairyland->AutoxitInit
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-27 09:21
 **/
@Component
public class AutoxitInit implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AutoxitInit.class);


    @Autowired
    private LinkerServer server;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("-----AutoxitInit 启动开始--------");

        Thread thread = new Thread(() -> {
            try {
                server.start();
            } catch (Exception e) {
                logger.error("start thread:{}", e.getMessage());

            }
        });
        thread.start();
        server.waitStarted();

        logger.info("-----AutoxitInit 启动完成--------");
    }
}
