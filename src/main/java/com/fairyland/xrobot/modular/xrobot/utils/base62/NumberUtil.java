package com.fairyland.xrobot.modular.xrobot.utils.base62;


import org.apache.commons.lang3.StringUtils;

public class NumberUtil {
    /**
     * 初始化 62 进制数据，索引位置代表字符的数值，比如 A代表10，z代表61等
     */
    //
    private static String chars58 = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale58 = 58;
    private static String chars62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale62 = 62;

    /**
     * 将数字转为62进制
     *
     * @param num          Long 型数字
     * @param formatLength 转换后的字符串长度，不足则左侧补0
     * @return 62进制字符串
     */
    public static String encode62(long num, int formatLength) {
        StringBuilder sb = new StringBuilder();
        int remainder = 0;

        while (num > scale62 - 1) {
            /**
             * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(num % scale62).intValue();
            sb.append(chars62.charAt(remainder));

            num = num / scale62;
        }

        sb.append(chars62.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, formatLength, '0');
    }

    /**
     * 58进制字符串转为数字
     *
     * @param str 编码后的58进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long decode62(String str) {
        /**
         * 将 0 开头的字符串进行替换
         */
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            /**
             * 查找字符的索引位置
             */
            index = chars62.indexOf(str.charAt(i));
            /**
             * 索引位置代表字符的数值
             */
            num += (long) (index * (Math.pow(scale62, str.length() - i - 1)));
        }

        return num;
    }

    /**
     * 将数字转为58进制
     *
     * @param num          Long 型数字
     * @param formatLength 转换后的字符串长度，不足则左侧补0
     * @return 58进制字符串
     */
    public static String encode58(long num, int formatLength) {
        StringBuilder sb = new StringBuilder();
        int remainder = 0;

        while (num > scale58 - 1) {
            /**
             * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(num % scale58).intValue();
            sb.append(chars58.charAt(remainder));

            num = num / scale58;
        }

        sb.append(chars58.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, formatLength, '0');
    }

    /**
     * 62进制字符串转为数字
     *
     * @param str 编码后的58进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long decode58(String str) {
        /**
         * 将 0 开头的字符串进行替换
         */
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            /**
             * 查找字符的索引位置
             */
            index = chars58.indexOf(str.charAt(i));
            /**
             * 索引位置代表字符的数值
             */
            num += (long) (index * (Math.pow(scale58, str.length() - i - 1)));
        }

        return num;
    }
}
