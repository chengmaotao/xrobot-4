package com.fairyland.xrobot.modular.system.controller;

import com.fairyland.xrobot.common.constant.Constants;
import com.fairyland.xrobot.common.utils.ServletUtils;
import com.fairyland.xrobot.framework.security.LoginUser;
import com.fairyland.xrobot.framework.security.service.SysLoginService;
import com.fairyland.xrobot.framework.security.service.SysPermissionService;
import com.fairyland.xrobot.framework.security.service.TokenService;
import com.fairyland.xrobot.framework.web.domain.AjaxResult;
import com.fairyland.xrobot.modular.system.domain.SysMenu;
import com.fairyland.xrobot.modular.system.domain.SysUser;
import com.fairyland.xrobot.modular.system.domain.vo.RouterVo;
import com.fairyland.xrobot.modular.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登录验证
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录方法
     *
     * @param username 用户名
     * @param password 密码
     * @param randstr  随机数（废弃）
     * @param ticket   票据（废弃）
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(String username, String password, String randstr, String ticket) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(username, password, randstr, ticket);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();

        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();

        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());

        List<RouterVo> routerVos = menuService.buildMenus(menus);

        AjaxResult success = AjaxResult.success(routerVos);

        return success;
    }

}
