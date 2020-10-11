package com.fairyland.xrobot.modular.xrobot.utils;

import com.alibaba.fastjson.JSON;
import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClientSubmitPushJoinGroupsReq;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.security.MessageDigest;
import java.text.ParseException;
import java.util.*;

/**
 * @program: fairyland->Utility
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-24 10:54
 **/
public class Utility {

    public static Map<String, String> monitorClientAppStatus;

    public static Map<String, String> taskClass;

    static {
        monitorClientAppStatus = new HashMap<>();

        monitorClientAppStatus.put("0", "未就绪");
        monitorClientAppStatus.put("100", "正常");
        monitorClientAppStatus.put("200", "客户端已暂停");
        monitorClientAppStatus.put("300", "Facebook账号被禁用");
        monitorClientAppStatus.put("301", "WhatsApp账号被禁用");


        taskClass = new HashMap<>();
        taskClass.put("100001", "搜索加群消息任务");
        taskClass.put("100002", "首页链接消息任务");
        taskClass.put("100003", "创建群组发帖任务");
        taskClass.put("100004", "搜索加群评论任务");
        taskClass.put("100005", "首页帖子评论任务");


    }

    public static String getTaskClassName(String key) {
        return taskClass.get(key);
    }

    public static String getMonitorClientAppStatus(String key) {
        return monitorClientAppStatus.get(key);
    }


    public static Date getNowDate() {
        return new Date();
    }

    // 获取系统当前时间(时间戳)
    public static int getCurrentTimeStamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }


    // 时间戳转日期
    public static String fmtYmdHms(int timestamp) {
        long ts = ((long) timestamp) * 1000;
        return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(ts);
    }


    public static String fmtDate(Date date) {
        if (date == null) {
            return "";
        }

        return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(date);

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


    public static String getMd5(String key) {
        if (StringUtils.isEmpty(key)) {
            return key;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(key.getBytes("UTF-8"));
            return Hex.encodeHexString(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }


    /**
     * @Description: 日期增加分钟
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2019/11/21 14:56
     */
    public static Date addMinute(Date date, int n) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.MINUTE, n);
        Date time = cd.getTime();
        return time;
    }

    public static void main(String[] args) {

/*        List<Device> list = new ArrayList<>();

        Device d = new Device();
        d.setDeviceid("id001");
        d.setDevicesn("10001");
        list.add(d);

        Device d2 = new Device();
        d2.setDeviceid("id002");
        d2.setDevicesn("10002");
        list.add(d2);

        JSONArray objects = JSONArray.parseArray(JSON.toJSONString(list));

        System.out.println(objects);*/


        String bodyString = "{\"id\":\"1\",\"phone\":\"11\",\"taskID\":\"111\",\"taskclass\":\"1111\",\"batch\":11111,\"user\":\"1111111\",\"keyword\":\"1234567\",\"join\":[{\"groupID\":\"23\",\"groupname\":\"34\",\"state\":2},{\"groupID\":\"45\",\"groupname\":\"67\",\"state\":3}]}";
        ClientSubmitPushJoinGroupsReq businessParam = JSON.parseObject(bodyString, ClientSubmitPushJoinGroupsReq.class);

        System.out.println(businessParam);


/*String aa = "你好\r\n" +
        "我也号\n" +
        "大家好哦\r";


// newString = myString.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
        String[] split = aa.split("(\\r\\n|\\r|\\n|\\n\\r)");

        System.out.println(split);*/

    }


}
