package com.fairyland.xrobot.modular.xrobot.domain;

public class Comments extends BaseEntity {

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_comments.taskID
     *
     * @mbggenerated
     */
    private String taskid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_comments.taskclass
     *
     * @mbggenerated
     */
    private String taskclass;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_comments.batch
     *
     * @mbggenerated
     */
    private Integer batch;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_comments.deviceID
     *
     * @mbggenerated
     */
    private String deviceid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_comments.groupname
     *
     * @mbggenerated
     */
    private String groupname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_comments.state
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_comments.groupname1
     *
     * @mbggenerated
     */
    private String groupname1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_comments.poster
     *
     * @mbggenerated
     */
    private String poster;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_comments.taskID
     *
     * @return the value of c_comments.taskID
     * @mbggenerated
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_comments.taskID
     *
     * @param taskid the value for c_comments.taskID
     * @mbggenerated
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_comments.taskclass
     *
     * @return the value of c_comments.taskclass
     * @mbggenerated
     */
    public String getTaskclass() {
        return taskclass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_comments.taskclass
     *
     * @param taskclass the value for c_comments.taskclass
     * @mbggenerated
     */
    public void setTaskclass(String taskclass) {
        this.taskclass = taskclass == null ? null : taskclass.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_comments.batch
     *
     * @return the value of c_comments.batch
     * @mbggenerated
     */
    public Integer getBatch() {
        return batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_comments.batch
     *
     * @param batch the value for c_comments.batch
     * @mbggenerated
     */
    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_comments.deviceID
     *
     * @return the value of c_comments.deviceID
     * @mbggenerated
     */
    public String getDeviceid() {
        return deviceid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_comments.deviceID
     *
     * @param deviceid the value for c_comments.deviceID
     * @mbggenerated
     */
    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_comments.groupname
     *
     * @return the value of c_comments.groupname
     * @mbggenerated
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_comments.groupname
     *
     * @param groupname the value for c_comments.groupname
     * @mbggenerated
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_comments.state
     *
     * @return the value of c_comments.state
     * @mbggenerated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_comments.state
     *
     * @param state the value for c_comments.state
     * @mbggenerated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_comments.groupname1
     *
     * @return the value of c_comments.groupname1
     * @mbggenerated
     */
    public String getGroupname1() {
        return groupname1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_comments.groupname1
     *
     * @param groupname1 the value for c_comments.groupname1
     * @mbggenerated
     */
    public void setGroupname1(String groupname1) {
        this.groupname1 = groupname1 == null ? null : groupname1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_comments.poster
     *
     * @return the value of c_comments.poster
     * @mbggenerated
     */
    public String getPoster() {
        return poster;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_comments.poster
     *
     * @param poster the value for c_comments.poster
     * @mbggenerated
     */
    public void setPoster(String poster) {
        this.poster = poster == null ? null : poster.trim();
    }
}