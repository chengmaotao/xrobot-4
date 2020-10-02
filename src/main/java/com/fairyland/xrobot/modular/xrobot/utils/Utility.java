package com.fairyland.xrobot.modular.xrobot.utils;

import com.fairyland.xrobot.common.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

/**
 * @program: fairyland->Utility
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-24 10:54
 **/
public class Utility {


    // 获取系统当前时间(时间戳)
    public static int getCurrentTimeStamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }


    // 时间戳转日期
    public static String fmtYmdHms(int timestamp) {
        long ts = ((long) timestamp) * 1000;
        return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(ts);
    }


    // 校验日期格式
    public static Date checkTradeTimeFormat(String tradeTime) {

        if (StringUtils.isEmpty(tradeTime)) {
            return null;
        }

        FastDateFormat instance = null;

        if (tradeTime.length() == 16) {
            instance = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");
        } else if (tradeTime.length() == 19) {
            instance = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        }

        if (instance == null) {
            return null;
        }


        try {
            return instance.parse(tradeTime);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static Date getyyyyMMddHHmmss(String date) {
        FastDateFormat instance = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

        try {
            return instance.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Integer getTimeStamp(Date date) {

        if (date == null) {
            return null;
        }

        return (int) (date.getTime() / 1000);

    }


    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static void main(String[] args) {

        String pwd = "admin";

        System.out.println(SecurityUtils.encryptPassword(pwd));


    }


}
