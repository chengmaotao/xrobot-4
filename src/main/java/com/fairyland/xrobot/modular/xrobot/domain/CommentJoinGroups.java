package com.fairyland.xrobot.modular.xrobot.domain;

import com.fairyland.xrobot.modular.xrobot.utils.Utility;

public class CommentJoinGroups extends BaseEntity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_commentjoingroups.taskID
     *
     * @mbggenerated
     */
    private String taskid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_commentjoingroups.taskclass
     *
     * @mbggenerated
     */
    private String taskclass;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_commentjoingroups.batch
     *
     * @mbggenerated
     */
    private Integer batch;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_commentjoingroups.deviceID
     *
     * @mbggenerated
     */
    private String deviceid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_commentjoingroups.groupID
     *
     * @mbggenerated
     */
    private String groupid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_commentjoingroups.groupname
     *
     * @mbggenerated
     */
    private String groupname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_commentjoingroups.state
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_commentjoingroups.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_commentjoingroups.taskID
     *
     * @return the value of c_commentjoingroups.taskID
     * @mbggenerated
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_commentjoingroups.taskID
     *
     * @param taskid the value for c_commentjoingroups.taskID
     * @mbggenerated
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_commentjoingroups.taskclass
     *
     * @return the value of c_commentjoingroups.taskclass
     * @mbggenerated
     */
    public String getTaskclass() {
        return taskclass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_commentjoingroups.taskclass
     *
     * @param taskclass the value for c_commentjoingroups.taskclass
     * @mbggenerated
     */
    public void setTaskclass(String taskclass) {
        this.taskclass = taskclass == null ? null : taskclass.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_commentjoingroups.batch
     *
     * @return the value of c_commentjoingroups.batch
     * @mbggenerated
     */
    public Integer getBatch() {
        return batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_commentjoingroups.batch
     *
     * @param batch the value for c_commentjoingroups.batch
     * @mbggenerated
     */
    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_commentjoingroups.deviceID
     *
     * @return the value of c_commentjoingroups.deviceID
     * @mbggenerated
     */
    public String getDeviceid() {
        return deviceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_commentjoingroups.deviceID
     *
     * @param deviceid the value for c_commentjoingroups.deviceID
     * @mbggenerated
     */
    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_commentjoingroups.groupID
     *
     * @return the value of c_commentjoingroups.groupID
     * @mbggenerated
     */
    public String getGroupid() {
        return groupid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_commentjoingroups.groupID
     *
     * @param groupid the value for c_commentjoingroups.groupID
     * @mbggenerated
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_commentjoingroups.groupname
     *
     * @return the value of c_commentjoingroups.groupname
     * @mbggenerated
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_commentjoingroups.groupname
     *
     * @param groupname the value for c_commentjoingroups.groupname
     * @mbggenerated
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_commentjoingroups.state
     *
     * @return the value of c_commentjoingroups.state
     * @mbggenerated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_commentjoingroups.state
     *
     * @param state the value for c_commentjoingroups.state
     * @mbggenerated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_commentjoingroups.phone
     *
     * @return the value of c_commentjoingroups.phone
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_commentjoingroups.phone
     *
     * @param phone the value for c_commentjoingroups.phone
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

    private String devicesn;

    public String getDevicesn() {
        return devicesn;
    }

    public void setDevicesn(String devicesn) {
        this.devicesn = devicesn;
    }


    private String md5;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}