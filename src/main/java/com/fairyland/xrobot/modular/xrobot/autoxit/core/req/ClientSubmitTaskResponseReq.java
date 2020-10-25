package com.fairyland.xrobot.modular.xrobot.autoxit.core.req;

import java.util.List;

/**
 * @program: fairyland->ClientSubmitTaskResponseReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-07 11:49
 **/
public class ClientSubmitTaskResponseReq extends BaseClientSubmitReq {


    private Integer error;

    private String errorMsg;

    private List<Extra> extra;

    public List<Extra> getExtra() {
        return extra;
    }

    public void setExtra(List<Extra> extra) {
        this.extra = extra;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ClientSubmitTaskResponseReq{" +
                "error=" + error +
                ", errorMsg='" + errorMsg + '\'' +
                ", extra=" + extra +
                '}';
    }


    public static class Extra {

        private String keyword;

        private String groupname;

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

        @Override
        public String toString() {
            return "Extra{" +
                    "keyword='" + keyword + '\'' +
                    ", groupname='" + groupname + '\'' +
                    '}';
        }
    }
}
