package com.fairyland.xrobot.modular.xrobot.autoxit.core.req;

/**
 * @program: fairyland->ClinetLoginReq
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-03 09:18
 **/
public class ClinetLoginReq {


    private String id; // 终端设备应用编号

    private String token; //

    private String phone; //

    private String account; //

    private String account1; //

    private String client; //

    private String status; //


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount1() {
        return account1;
    }

    public void setAccount1(String account1) {
        this.account1 = account1;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClinetLoginReq{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", account='" + account + '\'' +
                ", account1='" + account1 + '\'' +
                ", client='" + client + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
