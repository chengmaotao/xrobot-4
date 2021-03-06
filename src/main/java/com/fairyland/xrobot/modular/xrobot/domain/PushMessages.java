package com.fairyland.xrobot.modular.xrobot.domain;

import com.fairyland.xrobot.modular.xrobot.utils.Utility;

public class PushMessages extends BaseEntity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_pushmessages.taskID
     *
     * @mbggenerated
     */
    private String taskid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_pushmessages.taskclass
     *
     * @mbggenerated
     */
    private String taskclass;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_pushmessages.batch
     *
     * @mbggenerated
     */
    private Integer batch;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_pushmessages.deviceID
     *
     * @mbggenerated
     */
    private String deviceid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_pushmessages.groupname
     *
     * @mbggenerated
     */
    private String groupname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_pushmessages.groupname1
     *
     * @mbggenerated
     */
    private String groupname1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_pushmessages.usernumber
     *
     * @mbggenerated
     */
    private String usernumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_pushmessages.state
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_pushmessages.phone
     *
     * @mbggenerated
     */
    private String phone;


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_pushmessages.taskID
     *
     * @return the value of c_pushmessages.taskID
     * @mbggenerated
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_pushmessages.taskID
     *
     * @param taskid the value for c_pushmessages.taskID
     * @mbggenerated
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_pushmessages.taskclass
     *
     * @return the value of c_pushmessages.taskclass
     * @mbggenerated
     */
    public String getTaskclass() {
        return taskclass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_pushmessages.taskclass
     *
     * @param taskclass the value for c_pushmessages.taskclass
     * @mbggenerated
     */
    public void setTaskclass(String taskclass) {
        this.taskclass = taskclass == null ? null : taskclass.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_pushmessages.batch
     *
     * @return the value of c_pushmessages.batch
     * @mbggenerated
     */
    public Integer getBatch() {
        return batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_pushmessages.batch
     *
     * @param batch the value for c_pushmessages.batch
     * @mbggenerated
     */
    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_pushmessages.deviceID
     *
     * @return the value of c_pushmessages.deviceID
     * @mbggenerated
     */
    public String getDeviceid() {
        return deviceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_pushmessages.deviceID
     *
     * @param deviceid the value for c_pushmessages.deviceID
     * @mbggenerated
     */
    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_pushmessages.groupname
     *
     * @return the value of c_pushmessages.groupname
     * @mbggenerated
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_pushmessages.groupname
     *
     * @param groupname the value for c_pushmessages.groupname
     * @mbggenerated
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_pushmessages.groupname1
     *
     * @return the value of c_pushmessages.groupname1
     * @mbggenerated
     */
    public String getGroupname1() {
        return groupname1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_pushmessages.groupname1
     *
     * @param groupname1 the value for c_pushmessages.groupname1
     * @mbggenerated
     */
    public void setGroupname1(String groupname1) {
        this.groupname1 = groupname1 == null ? null : groupname1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_pushmessages.usernumber
     *
     * @return the value of c_pushmessages.usernumber
     * @mbggenerated
     */
    public String getUsernumber() {
        return usernumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_pushmessages.usernumber
     *
     * @param usernumber the value for c_pushmessages.usernumber
     * @mbggenerated
     */
    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber == null ? null : usernumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_pushmessages.state
     *
     * @return the value of c_pushmessages.state
     * @mbggenerated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_pushmessages.state
     *
     * @param state the value for c_pushmessages.state
     * @mbggenerated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_pushmessages.phone
     *
     * @return the value of c_pushmessages.phone
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_pushmessages.phone
     *
     * @param phone the value for c_pushmessages.phone
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    private String taskclassName;

    public String getTaskclassName() {
        return Utility.getTaskClassName(getTaskclass());
    }

    public void setTaskclassName(String taskclassName) {
        this.taskclassName = taskclassName;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String md5;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    private String devicesn;

    public String getDevicesn() {
        return devicesn;
    }

    public void setDevicesn(String devicesn) {
        this.devicesn = devicesn;
    }
}