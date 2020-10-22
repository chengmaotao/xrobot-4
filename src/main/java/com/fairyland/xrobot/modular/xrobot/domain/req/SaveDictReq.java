package com.fairyland.xrobot.modular.xrobot.domain.req;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.modular.xrobot.exception.BusinessException;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: fairyland->SaveDictReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-04 10:13
 **/
public class SaveDictReq {

    private Logger logger = LoggerFactory.getLogger(SaveDictReq.class);

    private Long id; // 为空时新增 不为空是修改

    private Integer maxAddGroupValue;

    private Integer maxSearchKeyValue;

    private Integer maxTaskIntervalValue;

    private String remarks; // 备注

    public void validate() {

        if (id == null) {
            logger.warn("SaveDictReq 系统字典 id = {} 不正确", id);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (maxAddGroupValue == null) {
            logger.warn("SaveDictReq 系统字典 一次加群最大值 maxAddGroupValue = {} 不正确", maxAddGroupValue);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (maxSearchKeyValue == null) {
            logger.warn("SaveDictReq 系统字典 关键字搜索最大值  maxSearchKeyValue = {} 不正确", maxSearchKeyValue);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (maxTaskIntervalValue == null) {
            logger.warn("SaveDictReq 系统字典 发消息最大任务间隔(60分钟内不允许重复发送）maxTaskIntervalValue = {} 不正确", maxTaskIntervalValue);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if (StringUtils.isNotEmpty(remarks) && remarks.length() > 255) {
            logger.warn("SaveDeviceReq 系统字典 备注 remarks = {} 不正确 超出长度限制255", remarks);
            throw new XRobotException(ErrorCode.ERROR_CODE_5);
        }

        if(maxAddGroupValue > 10){
            logger.warn("SaveDictReq 系统字典 一次加群最大值 maxAddGroupValue = {} 不能大于10 不正确", maxAddGroupValue);
            throw new BusinessException("创建群组任务值 不能大于10");
        }

        if(maxSearchKeyValue > 1){
            logger.warn("SaveDictReq 系统字典 关键字搜索最大值  maxSearchKeyValue = {} 不能大于1 不正确", maxSearchKeyValue);
            throw new BusinessException("最大搜索关键字数量 不能大于10");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxAddGroupValue() {
        return maxAddGroupValue;
    }

    public void setMaxAddGroupValue(Integer maxAddGroupValue) {
        this.maxAddGroupValue = maxAddGroupValue;
    }

    public Integer getMaxSearchKeyValue() {
        return maxSearchKeyValue;
    }

    public void setMaxSearchKeyValue(Integer maxSearchKeyValue) {
        this.maxSearchKeyValue = maxSearchKeyValue;
    }

    public Integer getMaxTaskIntervalValue() {
        return maxTaskIntervalValue;
    }

    public void setMaxTaskIntervalValue(Integer maxTaskIntervalValue) {
        this.maxTaskIntervalValue = maxTaskIntervalValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "SaveDictReq{" +
                "id=" + id +
                ", maxAddGroupValue=" + maxAddGroupValue +
                ", maxSearchKeyValue=" + maxSearchKeyValue +
                ", maxTaskIntervalValue=" + maxTaskIntervalValue +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
