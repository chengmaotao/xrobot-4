package com.fairyland.xrobot.modular.xrobot.autoxit.core.req;

import java.util.List;

/**
 * @program: fairyland->ClientSubmitCommentJoinGroupsReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-07 10:46
 **/
public class ClientSubmitCommentJoinGroupsReq extends BaseClientSubmitReq {

    private String keyword; //

    private List<GroupsInfo> join; //

    public static class GroupsInfo {

        private String groupID;
        private String groupname;
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

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "GroupsInfo{" +
                    "groupID='" + groupID + '\'' +
                    ", groupname='" + groupname + '\'' +
                    ", state=" + state +
                    '}';
        }
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<ClientSubmitCommentJoinGroupsReq.GroupsInfo> getJoin() {
        return join;
    }

    public void setJoin(List<ClientSubmitCommentJoinGroupsReq.GroupsInfo> join) {
        this.join = join;
    }


    @Override
    public String toString() {

        return "ClientSubmitCommentJoinGroupsReq{" +
                "keyword='" + keyword + '\'' +
                ", join=" + join +
                ", super.toString()=" + super.toString() +
                '}';
    }

}
