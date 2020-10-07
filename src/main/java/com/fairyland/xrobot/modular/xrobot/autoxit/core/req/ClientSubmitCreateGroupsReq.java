package com.fairyland.xrobot.modular.xrobot.autoxit.core.req;

/**
 * @program: fairyland->ClientSubmitCreateGroupsReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-07 11:37
 **/
public class ClientSubmitCreateGroupsReq extends BaseClientSubmitReq {

    private String keyword; //
    private String groupID; //
    private String groupname; //
    private Integer post;
    private Integer state;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "ClientSubmitCreateGroupsReq{" +
                "keyword='" + keyword + '\'' +
                ", groupID='" + groupID + '\'' +
                ", groupname='" + groupname + '\'' +
                ", post=" + post +
                ", state=" + state +
                "super.toString()=" + super.toString() +
                '}';
    }
}
