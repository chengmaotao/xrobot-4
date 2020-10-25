package com.fairyland.xrobot.modular.xrobot.domain.resp;

/**
 * @program: fairyland->ExportUserNumberListResp
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-26 02:31
 **/
public class ExportUserNumberListResp {

    private String usernumber;

    private Integer counts;

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "ExportUserNumberListResp{" +
                "usernumber='" + usernumber + '\'' +
                ", counts=" + counts +
                '}';
    }
}
