package com.fairyland.xrobot.framework.security.filter;

import com.fairyland.xrobot.common.constant.Constants;
import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.exception.TokenException;
import com.fairyland.xrobot.common.utils.MessageUtils;
import com.fairyland.xrobot.common.utils.SecurityUtils;
import com.fairyland.xrobot.common.utils.StringUtils;
import com.fairyland.xrobot.framework.security.LoginUser;
import com.fairyland.xrobot.framework.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private TokenService tokenService;

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        LoginUser loginUser = null;
        try {
            String token = request.getHeader(header);
            if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
                loginUser = tokenService.getLoginUser(request);
            }
        } catch (TokenException ex) {
            // 不做任何处理
            throw new TokenException(MessageUtils.message(messageSource, ErrorCode.ERROR_CODE_4));
        }


        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
