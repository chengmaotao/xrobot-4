package com.fairyland.xrobot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动程序
 */
@Slf4j
@ServletComponentScan
@EnableAsync
@ImportResource({"classpath:spring/spring.xml"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class XRobotApplication {
    public static void main(String[] args) {
        SpringApplication.run(XRobotApplication.class, args);
        log.info("。。。。。。【后台管理服务启动成功】。。。。。。");
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8");

        messageSource.setBasename("classpath:messages/message");

        return messageSource;
    }
}
