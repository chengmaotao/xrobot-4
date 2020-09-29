package com.fairyland.xrobot.common.utils;

import com.fairyland.xrobot.modular.xrobot.domain.resp.PageResult;
import com.github.pagehelper.PageInfo;

/**
 * @program: fairyland->PageUtils
 * @description: TODO
 * @author: ctc
 * @create: 2019-12-05 22:15
 **/
public class PageUtils {
    /**
     * 将分页信息封装到统一的接口
     *
     * @param pageInfo
     * @return
     */
    public static PageResult getPageResult(PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }
}
