package com.fairyland.xrobot.common.utils;

import com.fairyland.xrobot.common.exception.CustomException;
import io.credittag.infra.encryption.AES;

/**
 * @program: fairyland->AESUtils
 * @description: TODO
 * @author: ctc
 * @create: 2019-12-30 17:20
 **/
public class AESUtils {

    // aes 加密
    public static String aesEncrypt(byte[] key, byte[] text) {
        try {
            return AES.encrypt(key, text);
        } catch (Exception e) {
            throw new CustomException("Failed to process AES encryption.", e);
        }
    }
}
