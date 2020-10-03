package com.fairyland.xrobot.modular.xrobot.domain.req;

import java.io.Serializable;

/**
 * @program: fairyland->DeviceGroupMembersListReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 17:15
 **/
public class DeviceGroupMembersListReq extends PageRequest implements Serializable {

    private String groupid;  // 分组id

    private String groupname; // 分组名称

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public String toString() {
        return "DeviceGroupMembersListReq{" +
                "groupid='" + groupid + '\'' +
                ", groupname='" + groupname + '\'' +
                '}';
    }
}
