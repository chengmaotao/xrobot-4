package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->FileToQRCodeReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-15 21:57
 **/
public class FileToQRCodeReq {

    private Logger logger = LoggerFactory.getLogger(FileToQRCodeReq.class);

    private String context; // 文字转二维码


    public void validate() {

        if (StringUtils.isEmpty(context)) {
            logger.warn("FileToQRCodeReq context = null 不正确");
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
