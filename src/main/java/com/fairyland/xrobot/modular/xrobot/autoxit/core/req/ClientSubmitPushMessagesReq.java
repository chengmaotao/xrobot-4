package com.fairyland.xrobot.modular.xrobot.autoxit.core.req;

/**
 * @program: fairyland->ClientSubmitPushMessagesReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-07 10:26
 **/
public class ClientSubmitPushMessagesReq extends BaseClientSubmitReq {

    private String keyword; //

    private String groupname; //
    private String groupname1; //

    private String usernumber; //

    private Integer state;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupname1() {
        return groupname1;
    }

    public void setGroupname1(String groupname1) {
        this.groupname1 = groupname1;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ClientSubmitPushMessagesReq{" +
                "keyword='" + keyword + '\'' +
                ", groupname='" + groupname + '\'' +
                ", groupname1='" + groupname1 + '\'' +
                ", usernumber='" + usernumber + '\'' +
                ", state=" + state +
                "super.toString()=" + super.toString() +
                '}';
    }
}
