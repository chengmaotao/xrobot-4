package com.fairyland.xrobot.modular.xrobot.domain.req;

/**
 * @program: fairyland->PageRequest
 * @description: TODO
 * @author: ctc
 * @create: 2019-12-05 22:10
 **/
public class PageRequest {

    /**
     * 当前页码
     */
    private int pageNum = 1;
    /**
     * 每页数量
     */
    private int pageSize = 10;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
