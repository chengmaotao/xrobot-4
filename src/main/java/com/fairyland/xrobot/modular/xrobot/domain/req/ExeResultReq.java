package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->ExeResultReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-05 18:20
 **/
public class ExeResultReq extends PageRequest {

    private Logger logger = LoggerFactory.getLogger(ExeResultReq.class);

    private Integer code; // 1发消息加群结果表;2发消息执行结果表;3评论加群结果表;4评论执行结果表;5创建群组发帖任务执行结果表

    private String taskid;

    private String taskclass;

    private String phone;

    private String deviceid;

    private String beginTime; // 开始日期

    private String endTime; // 截止日期

    public void validate() {
        if (code == null || (code != 1 && code != 2 && code != 3 && code != 4 && code != 5)) {
            logger.warn("ExeResultReq code={} 不正确", code);

            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTaskclass() {
        return taskclass;
    }

    public void setTaskclass(String taskclass) {
        this.taskclass = taskclass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ExeResultReq{" +
                "code=" + code +
                ", taskid='" + taskid + '\'' +
                ", taskclass='" + taskclass + '\'' +
                ", phone='" + phone + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
