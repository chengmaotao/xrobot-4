package com.fairyland.xrobot.modular.xrobot.domain;

public class TasksWithBLOBs extends Tasks {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tasks.keywords
     *
     * @mbggenerated
     */
    private String keywords;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_tasks.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tasks.keywords
     *
     * @return the value of c_tasks.keywords
     *
     * @mbggenerated
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tasks.keywords
     *
     * @param keywords the value for c_tasks.keywords
     *
     * @mbggenerated
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_tasks.content
     *
     * @return the value of c_tasks.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_tasks.content
     *
     * @param content the value for c_tasks.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }


    private String taskclassName;

    public String getTaskclassName() {
        return taskclassName;
    }

    public void setTaskclassName(String taskclassName) {
        this.taskclassName = taskclassName;
    }
}