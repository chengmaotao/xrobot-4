package com.fairyland.xrobot.modular.xrobot.domain.resp;

import com.fairyland.xrobot.modular.xrobot.utils.Utility;

/**
 * @program: fairyland->BaseResp
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-25 10:41
 **/
public class BaseResp {


    public Integer updateDate;  // 时间戳

    public String operationDate;  // 日期

    public String operationUser;


    public String getOperationDate() {

        if (updateDate == null) {
            return "";
        }

        return Utility.fmtYmdHms(updateDate);
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public Integer getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Integer updateDate) {
        this.updateDate = updateDate;
    }
}
