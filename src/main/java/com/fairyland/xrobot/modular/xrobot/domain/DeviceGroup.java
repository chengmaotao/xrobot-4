package com.fairyland.xrobot.modular.xrobot.domain;

public class DeviceGroup extends BaseEntity {

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_devicegroup.groupID
     *
     * @mbggenerated
     */
    private String groupid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_devicegroup.groupname
     *
     * @mbggenerated
     */
    private String groupname;


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_devicegroup.groupID
     *
     * @return the value of c_devicegroup.groupID
     * @mbggenerated
     */
    public String getGroupid() {
        return groupid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_devicegroup.groupID
     *
     * @param groupid the value for c_devicegroup.groupID
     * @mbggenerated
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_devicegroup.groupname
     *
     * @return the value of c_devicegroup.groupname
     * @mbggenerated
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_devicegroup.groupname
     *
     * @param groupname the value for c_devicegroup.groupname
     * @mbggenerated
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }
}