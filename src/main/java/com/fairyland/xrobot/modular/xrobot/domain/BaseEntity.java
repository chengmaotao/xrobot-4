package com.fairyland.xrobot.modular.xrobot.domain;

import com.fairyland.xrobot.common.constant.XRobotCode;
import com.fairyland.xrobot.modular.system.domain.SysUser;
import com.fairyland.xrobot.modular.xrobot.utils.Utility;

/**
 * @program: fairyland->BaseEntity
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-24 18:24
 **/
public class BaseEntity {

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column x_sys_category.uid
     *
     * @mbggenerated
     */
    public Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column x_sys_category.create_by
     *
     * @mbggenerated
     */
    public String createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column x_sys_category.createtime
     *
     * @mbggenerated
     */
    public Integer createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column x_sys_category.update_by
     *
     * @mbggenerated
     */
    public String updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column x_sys_category.updatetime
     *
     * @mbggenerated
     */
    public Integer updatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column x_sys_category.remarks
     *
     * @mbggenerated
     */
    public String remarks;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column x_sys_category.isvalid
     *
     * @mbggenerated
     */
    public String isvalid;


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column x_sys_category.uid
     *
     * @return the value of x_sys_category.uid
     * @mbggenerated
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column x_sys_category.uid
     *
     * @param uid the value for x_sys_category.uid
     * @mbggenerated
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column x_sys_category.create_by
     *
     * @return the value of x_sys_category.create_by
     * @mbggenerated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column x_sys_category.create_by
     *
     * @param createBy the value for x_sys_category.create_by
     * @mbggenerated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column x_sys_category.createtime
     *
     * @return the value of x_sys_category.createtime
     * @mbggenerated
     */
    public Integer getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column x_sys_category.createtime
     *
     * @param createtime the value for x_sys_category.createtime
     * @mbggenerated
     */
    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column x_sys_category.update_by
     *
     * @return the value of x_sys_category.update_by
     * @mbggenerated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column x_sys_category.update_by
     *
     * @param updateBy the value for x_sys_category.update_by
     * @mbggenerated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column x_sys_category.updatetime
     *
     * @return the value of x_sys_category.updatetime
     * @mbggenerated
     */
    public Integer getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column x_sys_category.updatetime
     *
     * @param updatetime the value for x_sys_category.updatetime
     * @mbggenerated
     */
    public void setUpdatetime(Integer updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column x_sys_category.remarks
     *
     * @return the value of x_sys_category.remarks
     * @mbggenerated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column x_sys_category.remarks
     *
     * @param remarks the value for x_sys_category.remarks
     * @mbggenerated
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column x_sys_category.isvalid
     *
     * @return the value of x_sys_category.isvalid
     * @mbggenerated
     */
    public String getIsvalid() {
        return isvalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column x_sys_category.isvalid
     *
     * @param isvalid the value for x_sys_category.isvalid
     * @mbggenerated
     */
    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid == null ? null : isvalid.trim();
    }


    public void preUpdate(SysUser user) {

        setUpdateBy(user.getUserName());

        setUpdatetime(Utility.getCurrentTimeStamp());
    }

    public void preInsert(SysUser user) {

        setCreateBy(user.getUserName());
        setCreatetime(Utility.getCurrentTimeStamp());

        setUpdateBy(getCreateBy());

        setUpdatetime(getCreatetime());

        setIsvalid(XRobotCode.GOD_Y);

    }


    public String operationDate;  // 日期

    public String operationUser;


    public String getOperationDate() {
        if (updatetime == null) {
            return "";
        }
        return Utility.fmtYmdHms(updatetime);
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationUser() {
        return updateBy;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }
}
