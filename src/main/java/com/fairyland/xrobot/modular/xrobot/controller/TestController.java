package com.fairyland.xrobot.modular.xrobot.controller;

import com.fairyland.xrobot.modular.xrobot.dao.mapper.AppVersionMapper;
import com.fairyland.xrobot.modular.xrobot.domain.AppVersion;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: fairyland->TestController
 * @description: TODO
 * @author: ctc
 * @create: 2020-10-13 17:14
 **/
@RestController
public class TestController {


    @Autowired
    private AppVersionMapper appVersionMapper;

    @RequestMapping(value = "/test")
    public String test() {



       /* AppVersion a = new AppVersion();
        a.setOsname("M\uD83D\uDC9EL Pinhole Camera (暗访针孔摄像头)");
        a.setAppversion("111");
        a.setContext("congter测试");
        a.setTitle("tiest测试");
        a.setForceflag("0");
        a.setDownloadurl("xxx");
        a.setCreateBy("11");
        a.setCreateDate(new Date());
        a.setUpdateBy("22");
        a.setUpdateDate(new Date());
        a.setDelFlag("0");

        appVersionMapper.insertSelective(a);
*/
        AppVersion appVersion = appVersionMapper.selectByPrimaryKey(17L);


        String str=appVersion.getOsname();
        //String result= EmojiParser.parseToAliases(str);

        String res=EmojiParser.parseToUnicode(str);


        return res;

    }

}
