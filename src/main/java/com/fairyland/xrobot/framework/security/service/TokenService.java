package com.fairyland.xrobot.framework.security.service;

import com.alibaba.fastjson.JSONObject;
import com.fairyland.xrobot.common.constant.Constants;
import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.exception.TokenException;
import com.fairyland.xrobot.common.utils.*;
import com.fairyland.xrobot.common.utils.ip.AddressUtils;
import com.fairyland.xrobot.common.utils.ip.IpUtils;
import com.fairyland.xrobot.framework.redis.RedisCache;
import com.fairyland.xrobot.framework.security.LoginUser;
import com.fairyland.xrobot.modular.system.domain.SysUser;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 */
@Component
public class TokenService {

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    private Logger logger = LoggerFactory.getLogger(TokenService.class);
    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    @Value("${token.loginExpireTime}")
    private int loginExpireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final int MILLIS_MINUTE_TEN = 20;

    @Autowired
    private RedisCache redisCache;


    private Map<String, Object> parseTokenGetSecrcet(HttpServletRequest request) {
        try {
            String token = getToken(request);

            if (StringUtils.isNotEmpty(token)) {
                // 根据token,base64解码 获得 加密key
                String[] tokensInfo = token.split(Constants.JWT_SPLIT);

                String tokenBody = tokensInfo[1];

                String bodyJson = Base64URLUtils.decode(tokenBody);

                JSONObject tokenBodyJson = JSONObject.parseObject(bodyJson);

                String tokenSecretKey = tokenBodyJson.getString(Constants.LOGIN_USER_KEY);

                String tokenIp = tokenBodyJson.getString(Constants.LOGIN_IP_KEY);

                String tokenOs = tokenBodyJson.getString(Constants.LOGIN_OS_KEY);

                String tokenBrowser = tokenBodyJson.getString(Constants.LOGIN_BROWSER_KEY);


                String reqIp = IpUtils.getIpAddr(request);
                if (!StringUtils.equals(tokenIp, reqIp)) {
                    logger.info("请求ip={} 和tokenip = {} 不一致", reqIp, tokenIp);
                    throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
                }

                UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

                String reqBrowserName = userAgent.getBrowser().getName();
                if (!StringUtils.equals(reqBrowserName, tokenBrowser)) {
                    logger.info("请求浏览器={} 和token浏览器 = {} 名称不一致", reqBrowserName, tokenBrowser);
                    throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
                }

                String reqOsName = userAgent.getOperatingSystem().getName();
                if (!StringUtils.equals(reqOsName, tokenOs)) {
                    logger.info("请求系统={} 和token系统 = {} 名称不一致", reqOsName, tokenOs);
                    throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
                }


                Map<String, Object> loginUserInfo = redisCache.getTokenCacheObject(tokenSecretKey);

                // jwt 加密 秘钥 redisKey
                if (loginUserInfo == null) {
                    logger.info("redis 获取token秘钥过期了：tokenSecretKey = {}", tokenSecretKey);
                    throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
                }
                return loginUserInfo;
            }

            logger.info("httpRequest  head里 没有获取到 token");
            throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));

        } catch (TokenException ex) {
            throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("根据token 获得tokenSecret 失败：ex = {}", ex);
            throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
        }
    }


    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {


        try {
            // 获取请求携带的令牌
            String token = getToken(request);
            Map<String, Object> loginUserInfo = parseTokenGetSecrcet(request);

            String tokenSecret = (String) loginUserInfo.get("tokenSecret");
            parseToken(token, tokenSecret);

            LoginUser loginUser = (LoginUser) loginUserInfo.get("login_user");

            return loginUser;
        } catch (TokenException ex) {
            throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("根据token  getLoginUser 失败：ex = {}", ex);
        }
        return null;
    }


    /**
     * @Description: 创建令牌
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2019/11/28 17:10
     */
    public String createGodToken(LoginUser loginUser) {

        // userid_UUID
        String tokenSecretKey = new StringBuffer("xrobot_auth_token_").append(loginUser.getUser().getUserId()).append("_").append(IdUtils.fastUUID()).toString();
        loginUser.setToken(tokenSecretKey);
        setUserAgent(loginUser);
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + loginExpireTime * MILLIS_MINUTE);


        // 将 secret 和 loginUser 存入到 redis中 key=tokenSecretKey

        String tokenSecret = IdUtils.fastUUID();

        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("tokenSecret", tokenSecret);
        tokenInfo.put("login_user", loginUser);
        //tokenInfo.put("login_user", new UserInfo());

        redisCache.set(tokenSecretKey, tokenInfo, expireTime, TimeUnit.MINUTES);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, tokenSecretKey);
        claims.put(Constants.LOGIN_IP_KEY, loginUser.getIpaddr());
        claims.put(Constants.LOGIN_OS_KEY, loginUser.getOs());
        claims.put(Constants.LOGIN_BROWSER_KEY, loginUser.getBrowser());
        //claims.put(Constants.LOGIN_USER, loginUser);
        return createGodToken(claims, tokenSecret);
    }

    private String createGodToken(Map<String, Object> claims, String secret) {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser 令牌
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser) {

        String tokenSecretKey = loginUser.getToken();

        // 单位分钟
        long expire = redisCache.getExpire(tokenSecretKey);

        // 相差不足20分钟，自动刷新缓存
        if (expire <= MILLIS_MINUTE_TEN) {
            redisCache.expire(tokenSecretKey, expireTime);
        }
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }


    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token, String secret) {
        Claims claims = parseToken(token, secret);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        } else {
            token = request.getParameter("token");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }

    public void updateToken(HttpServletRequest request, SysUser sysUser, String avatar) {

        try {
            String token = getToken(request);

            if (StringUtils.isNotEmpty(token)) {
                // 根据token,base64解码 获得 加密key
                String[] tokensInfo = token.split(Constants.JWT_SPLIT);

                String tokenBody = tokensInfo[1];

                String bodyJson = Base64URLUtils.decode(tokenBody);

                JSONObject tokenBodyJson = JSONObject.parseObject(bodyJson);

                String tokenSecretKey = tokenBodyJson.getString(Constants.LOGIN_USER_KEY);

                Map<String, Object> loginUserInfo = redisCache.getTokenCacheObject(tokenSecretKey);

                // jwt 加密 秘钥 redisKey
                if (loginUserInfo == null) {
                    logger.info("redis 获取token秘钥过期了：tokenSecretKey = {}", tokenSecretKey);
                    throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
                }
                LoginUser loginUser = (LoginUser) loginUserInfo.get("login_user");
                if (sysUser != null) {
                    loginUser.setUser(sysUser);
                }
                if (avatar != null) {
                    loginUser.getUser().setAvatar(avatar);
                }
                loginUserInfo.put("login_user", loginUser);

                redisCache.set(tokenSecretKey, loginUserInfo, expireTime, TimeUnit.MINUTES);
            } else {
                logger.info("httpRequest  head里 没有获取到 token");
                throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
            }

        } catch (TokenException ex) {
            throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("根据token 获得tokenSecret 失败：ex = {}", ex);
            throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
        }

    }


}
