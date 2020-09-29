package com.fairyland.xrobot.framework.security.service;

import com.alibaba.fastjson.JSONObject;
import com.fairyland.xrobot.common.constant.Constants;
import com.fairyland.xrobot.common.exception.CustomException;
import com.fairyland.xrobot.common.exception.user.CaptchaException;
import com.fairyland.xrobot.common.exception.user.UserPasswordNotMatchException;
import com.fairyland.xrobot.common.utils.MessageUtils;
import com.fairyland.xrobot.common.utils.ServletUtils;
import com.fairyland.xrobot.common.utils.ip.IpUtils;
import com.fairyland.xrobot.framework.manager.AsyncManager;
import com.fairyland.xrobot.framework.manager.factory.AsyncFactory;
import com.fairyland.xrobot.framework.security.LoginUser;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import com.fairyland.xrobot.modular.xrobot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String login(String username, String password, String randstr, String ticket) {

        JSONObject captchaParamsJson = userService.getCaptchaParamsJson();

        captchaParamsJson.put("Ticket", ticket);
        captchaParamsJson.put("UserIp", IpUtils.getIpAddr(ServletUtils.getRequest()));
        captchaParamsJson.put("Randstr", randstr);

        try {
            userService.checkCaptcha(captchaParamsJson);
        } catch (XRobotException ex) {
            throw new CaptchaException();
        }

        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message(messageSource, "user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message(messageSource, "user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();


        // 生成token
        //return tokenService.createToken(loginUser);
        return tokenService.createGodToken(loginUser);
    }
}
