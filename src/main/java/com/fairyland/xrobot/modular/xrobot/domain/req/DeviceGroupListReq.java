package com.fairyland.xrobot.modular.xrobot.domain.req;

import java.io.Serializable;

/**
 * @program: fairyland->DeviceGroupListReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-02 16:22
 **/
public class DeviceGroupListReq extends PageRequest implements Serializable {

    private String groupname; // 分组名称

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public String toString() {
        return "DeviceGroupListReq{" +
                "groupname='" + groupname + '\'' +
                '}';
    }
}
