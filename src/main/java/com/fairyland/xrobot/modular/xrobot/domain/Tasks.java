package com.fairyland.xrobot.modular.xrobot.domain;

import com.fairyland.xrobot.common.utils.StringUtils;

public class Tasks extends BaseEntity {

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tasks.taskID
     *
     * @mbggenerated
     */
    private String taskid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tasks.taskclass
     *
     * @mbggenerated
     */
    private String taskclass;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tasks.batch
     *
     * @mbggenerated
     */
    private Integer batch;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tasks.md5
     *
     * @mbggenerated
     */
    private String md5;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tasks.cover
     *
     * @mbggenerated
     */
    private String cover;


    private String coverUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tasks.state
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tasks.taskID
     *
     * @return the value of c_tasks.taskID
     * @mbggenerated
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tasks.taskID
     *
     * @param taskid the value for c_tasks.taskID
     * @mbggenerated
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tasks.taskclass
     *
     * @return the value of c_tasks.taskclass
     * @mbggenerated
     */
    public String getTaskclass() {
        return taskclass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tasks.taskclass
     *
     * @param taskclass the value for c_tasks.taskclass
     * @mbggenerated
     */
    public void setTaskclass(String taskclass) {
        this.taskclass = taskclass == null ? null : taskclass.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tasks.batch
     *
     * @return the value of c_tasks.batch
     * @mbggenerated
     */
    public Integer getBatch() {
        return batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tasks.batch
     *
     * @param batch the value for c_tasks.batch
     * @mbggenerated
     */
    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tasks.md5
     *
     * @return the value of c_tasks.md5
     * @mbggenerated
     */
    public String getMd5() {
        return md5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tasks.md5
     *
     * @param md5 the value for c_tasks.md5
     * @mbggenerated
     */
    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tasks.cover
     *
     * @return the value of c_tasks.cover
     * @mbggenerated
     */
    public String getCover() {
        return cover;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tasks.cover
     *
     * @param cover the value for c_tasks.cover
     * @mbggenerated
     */
    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tasks.state
     *
     * @return the value of c_tasks.state
     * @mbggenerated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tasks.state
     *
     * @param state the value for c_tasks.state
     * @mbggenerated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public String getCoverUrl() {

        if (StringUtils.isNotEmpty(cover)) {
            return "http://39.99.233.24:20001" + cover;

            //return "http://148.66.129.158:20001" + cover;
        }
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    private Integer delay;

    private Integer deadline;

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    private String groupname;

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    private Integer nolinks; // 无新链接时间:XXX 分钟（结束当前任务） 默认30分钟

    public Integer getNolinks() {
        return nolinks;
    }

    public void setNolinks(Integer nolinks) {
        this.nolinks = nolinks;
    }
}