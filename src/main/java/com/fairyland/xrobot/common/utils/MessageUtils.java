package com.fairyland.xrobot.common.utils;

import com.fairyland.xrobot.common.constant.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * 获取i18n资源文件
 */

public class MessageUtils {

    private static Logger logger = LoggerFactory.getLogger(MessageUtils.class);

    public static String message(ReloadableResourceBundleMessageSource messageSource, String code) {
        return getMessage(messageSource, code, null);
    }

    public static String message(ReloadableResourceBundleMessageSource messageSource, String code, Object[] args) {

        return getMessage(messageSource, code, args, Locale.SIMPLIFIED_CHINESE);
    }

    public static String message(ReloadableResourceBundleMessageSource messageSource, int code) {

        String status = "response.failure.sysfail";

        switch (code) {
            case ErrorCode.ERROR_CODE_4: {
                status = "api000004";
                break;
            }
            case ErrorCode.ERROR_CODE_5: {
                status = "api000005";
                break;
            }

            case ErrorCode.ERROR_CODE_6: {
                status = "api000006";
                break;
            }

            case ErrorCode.ERROR_CODE_20: {
                status = "api000020";
                break;
            }
            case ErrorCode.ERROR_CODE_21: {
                status = "api000021";
                break;
            }



            case ErrorCode.SYS_FAIL: {
                status = "response.failure.sysfail";
                break;
            }

        }

        return getMessage(messageSource, status, null);
    }


    public static String getMessage(ReloadableResourceBundleMessageSource messageSource, String code, String language) {
        return getMessage(messageSource, code, language, null);
    }

    public static String getMessage(ReloadableResourceBundleMessageSource messageSource, String code, String language, Object... args) {

        if (StringUtils.isEmpty(language)) {
            return getMessage(messageSource, code, Locale.SIMPLIFIED_CHINESE, args);
        } else if (StringUtils.equalsIgnoreCase("en", language)) {
            return getMessage(messageSource, code, Locale.US, args);
        }

        return getMessage(messageSource, code, Locale.SIMPLIFIED_CHINESE, args);
    }


    public static String getMessage(ReloadableResourceBundleMessageSource messageSource, String code, Locale locale, Object... args) {
        if (messageSource != null && StringUtils.isNotEmpty(code)) {
            try {
                return messageSource.getMessage(code, args, locale);
            } catch (Exception var5) {
                logger.warn("getMessage1: catch e.msg={}", var5.getMessage());
                return "";
            }
        } else {
            return "";
        }
    }


    public static String getMessage(ReloadableResourceBundleMessageSource messageSource, String code, Object[] args, Locale locale) {
        if (messageSource != null && StringUtils.isNotEmpty(code)) {
            try {
                return messageSource.getMessage(code, args, locale);
            } catch (Exception var5) {
                logger.warn("getMessage2: catch e.msg={}", var5.getMessage());
                return "";
            }
        } else {
            return "";
        }
    }

    public static String message(String code, Object[] args) {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8");

        messageSource.setBasename("classpath:messages/message");

        return message(messageSource, code, args);

    }
}
