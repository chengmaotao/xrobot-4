package com.fairyland.xrobot.modular.xrobot.domain;

import java.util.Date;

public class TaskDevicesLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.create_by
     *
     * @mbggenerated
     */
    private String createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.create_date
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.update_by
     *
     * @mbggenerated
     */
    private String updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.update_date
     *
     * @mbggenerated
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.remarks
     *
     * @mbggenerated
     */
    private String remarks;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.del_flag
     *
     * @mbggenerated
     */
    private String delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.taskID
     *
     * @mbggenerated
     */
    private String taskid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.batch
     *
     * @mbggenerated
     */
    private Integer batch;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.deviceID
     *
     * @mbggenerated
     */
    private String deviceid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.state
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.error
     *
     * @mbggenerated
     */
    private Integer error;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.errorMsg
     *
     * @mbggenerated
     */
    private String errormsg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.starttime
     *
     * @mbggenerated
     */
    private Date starttime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_taskdeviceslog.endtime
     *
     * @mbggenerated
     */
    private Date endtime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.id
     *
     * @return the value of c_taskdeviceslog.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.id
     *
     * @param id the value for c_taskdeviceslog.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.create_by
     *
     * @return the value of c_taskdeviceslog.create_by
     *
     * @mbggenerated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.create_by
     *
     * @param createBy the value for c_taskdeviceslog.create_by
     *
     * @mbggenerated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.create_date
     *
     * @return the value of c_taskdeviceslog.create_date
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.create_date
     *
     * @param createDate the value for c_taskdeviceslog.create_date
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.update_by
     *
     * @return the value of c_taskdeviceslog.update_by
     *
     * @mbggenerated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.update_by
     *
     * @param updateBy the value for c_taskdeviceslog.update_by
     *
     * @mbggenerated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.update_date
     *
     * @return the value of c_taskdeviceslog.update_date
     *
     * @mbggenerated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.update_date
     *
     * @param updateDate the value for c_taskdeviceslog.update_date
     *
     * @mbggenerated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.remarks
     *
     * @return the value of c_taskdeviceslog.remarks
     *
     * @mbggenerated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.remarks
     *
     * @param remarks the value for c_taskdeviceslog.remarks
     *
     * @mbggenerated
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.del_flag
     *
     * @return the value of c_taskdeviceslog.del_flag
     *
     * @mbggenerated
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.del_flag
     *
     * @param delFlag the value for c_taskdeviceslog.del_flag
     *
     * @mbggenerated
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.taskID
     *
     * @return the value of c_taskdeviceslog.taskID
     *
     * @mbggenerated
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.taskID
     *
     * @param taskid the value for c_taskdeviceslog.taskID
     *
     * @mbggenerated
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.batch
     *
     * @return the value of c_taskdeviceslog.batch
     *
     * @mbggenerated
     */
    public Integer getBatch() {
        return batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.batch
     *
     * @param batch the value for c_taskdeviceslog.batch
     *
     * @mbggenerated
     */
    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.deviceID
     *
     * @return the value of c_taskdeviceslog.deviceID
     *
     * @mbggenerated
     */
    public String getDeviceid() {
        return deviceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.deviceID
     *
     * @param deviceid the value for c_taskdeviceslog.deviceID
     *
     * @mbggenerated
     */
    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.state
     *
     * @return the value of c_taskdeviceslog.state
     *
     * @mbggenerated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.state
     *
     * @param state the value for c_taskdeviceslog.state
     *
     * @mbggenerated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.error
     *
     * @return the value of c_taskdeviceslog.error
     *
     * @mbggenerated
     */
    public Integer getError() {
        return error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.error
     *
     * @param error the value for c_taskdeviceslog.error
     *
     * @mbggenerated
     */
    public void setError(Integer error) {
        this.error = error;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.errorMsg
     *
     * @return the value of c_taskdeviceslog.errorMsg
     *
     * @mbggenerated
     */
    public String getErrormsg() {
        return errormsg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.errorMsg
     *
     * @param errormsg the value for c_taskdeviceslog.errorMsg
     *
     * @mbggenerated
     */
    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg == null ? null : errormsg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.starttime
     *
     * @return the value of c_taskdeviceslog.starttime
     *
     * @mbggenerated
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.starttime
     *
     * @param starttime the value for c_taskdeviceslog.starttime
     *
     * @mbggenerated
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_taskdeviceslog.endtime
     *
     * @return the value of c_taskdeviceslog.endtime
     *
     * @mbggenerated
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_taskdeviceslog.endtime
     *
     * @param endtime the value for c_taskdeviceslog.endtime
     *
     * @mbggenerated
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}