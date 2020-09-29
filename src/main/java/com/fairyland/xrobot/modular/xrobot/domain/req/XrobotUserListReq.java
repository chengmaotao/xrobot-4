package com.fairyland.xrobot.modular.xrobot.domain.req;

/**
 * @program: fairyland->XrobotUserListReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-22 15:38
 **/
public class XrobotUserListReq extends PageRequest {

    private String userName; //昵称 模糊匹配

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "XrobotUserListReq{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
