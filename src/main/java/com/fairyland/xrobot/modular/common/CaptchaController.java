package com.fairyland.xrobot.modular.common;

import com.fairyland.xrobot.common.constant.Constants;
import com.fairyland.xrobot.common.utils.IdUtils;
import com.fairyland.xrobot.framework.redis.RedisCache;
import com.fairyland.xrobot.framework.web.domain.AjaxResult;
import com.fairyland.xrobot.modular.xrobot.utils.captcha.RandomVerifyImgCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 */
@RestController
public class CaptchaController {


    @Value("${verifySize}")
    private int verifySize;

    @Value("${captchaImagew}")
    private int captchaImagew;

    @Value("${captchaImageh}")
    private int captchaImageh;

    @Autowired
    private RedisCache redisCache;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException {

        // 生成随机字串
        String verifyCode = RandomVerifyImgCodeUtils.generateVerifyCode(verifySize);

        String base64Img = RandomVerifyImgCodeUtils.outputImage(captchaImagew, captchaImageh, verifyCode);

        // 唯一标识
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        redisCache.set(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        try {
            AjaxResult ajax = AjaxResult.success();
            ajax.put("uuid", uuid);
            ajax.put("img", base64Img);
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
