package com.fairyland.xrobot.modular.xrobot.domain.req;

import java.io.Serializable;

/**
 * @program: fairyland->GroupnameListReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-26 01:48
 **/
public class GroupnameListReq extends PageRequest implements Serializable {


    private String keyword;

    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "GroupnameListReq{" +
                "keyword='" + keyword + '\'' +
                '}';
    }
}
