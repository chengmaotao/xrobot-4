package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->AppUploadReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-10 00:55
 **/
public class AppUploadReq {

    private Logger logger = LoggerFactory.getLogger(AppUploadReq.class);

    private String context; // 升级内容： 默认  页面优化，精彩内容等你来看。

    private String title; // 升级标题： 默认 新版本升级

    private String forceflag;  // 是否强制更新 Y 是 N不是，默认是N

    private String appversion; // app 版本号 默认 1.0.0

    private String osname; // 默认 Android

    public void validate() {

        if (StringUtils.isEmpty(context)) {
            context = "页面优化，精彩内容等你来看。";
        }

        if (StringUtils.isEmpty(title)) {
            title = "新版本升级";
        }

        if (StringUtils.isEmpty(forceflag)) {
            forceflag = "N";
        }

        if (StringUtils.isEmpty(appversion)) {
            appversion = "1.0.0";
        }

        if (StringUtils.isEmpty(osname)) {
            osname = "Android";
        }

        if (context.length() > 256 || title.length() > 128 || (!StringUtils.equals(forceflag, "N") && !StringUtils.equals(forceflag, "Y")) || appversion.length() > 8) {
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

    }

    public String getOsname() {
        return osname;
    }

    public void setOsname(String osname) {
        this.osname = osname;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForceflag() {
        return forceflag;
    }

    public void setForceflag(String forceflag) {
        this.forceflag = forceflag;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    @Override
    public String toString() {
        return "AppUploadReq{" +
                "context='" + context + '\'' +
                ", title='" + title + '\'' +
                ", forceflag='" + forceflag + '\'' +
                ", appversion='" + appversion + '\'' +
                ", osname='" + osname + '\'' +
                '}';
    }
}
