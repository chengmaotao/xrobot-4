package com.fairyland.xrobot.modular.xrobot.controller;

import com.fairyland.xrobot.common.constant.ErrorCode;
import com.fairyland.xrobot.common.utils.MessageUtils;
import com.fairyland.xrobot.common.utils.PageUtils;
import com.fairyland.xrobot.framework.web.domain.AjaxResult;
import com.fairyland.xrobot.modular.system.domain.SysRole;
import com.fairyland.xrobot.modular.system.domain.SysUser;
import com.fairyland.xrobot.modular.system.service.ISysRoleService;
import com.fairyland.xrobot.modular.xrobot.domain.req.XrobotUserListReq;
import com.fairyland.xrobot.modular.xrobot.domain.resp.PageResult;
import com.fairyland.xrobot.modular.xrobot.exception.XRobotException;
import com.fairyland.xrobot.modular.xrobot.service.UserService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: fairyland->UserController
 * @description: TODO
 * @author: ctc
 * @create: 2020-05-22 13:38
 **/
@RestController
@RequestMapping(value = "/xrobot/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;


    @Autowired
    private UserService userService;

    @Autowired
    private ISysRoleService roleService;

    /**
     * @Description: 用户列表(admin 用户不列出)
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/5/22 15:38
     */
    @PreAuthorize("@ss.hasPermi('user:list')")
    @RequestMapping("/userList")
    public AjaxResult userList(@RequestBody XrobotUserListReq params) {
        AjaxResult ajaxResult;
        try {

            List<SysUser> list = userService.userList(params);

            PageInfo<SysUser> pageInfo = new PageInfo<>(list);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            ajaxResult = AjaxResult.success(pageResult);

        } catch (XRobotException ex) {
            logger.warn("userList XRobotException = {}", ex);
            ajaxResult = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (Exception ex) {
            logger.error("userList Exception = {}", ex);
            ajaxResult = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }
        return ajaxResult;
    }


    /**
     * @Description: 普通角色列表（名称里包含管理员的都过滤掉了）
     * @Param:
     * @return:
     * @Author: ctc
     * @Date: 2020/5/22 16:34
     */
    @PreAuthorize("@ss.hasPermi('role:list')")
    @RequestMapping("/roleList")
    public AjaxResult roleList() {
        AjaxResult ajaxResult;
        try {

            List<SysRole> resp = roleService.xrobotRoleList();

            ajaxResult = AjaxResult.success(resp);

        } catch (XRobotException ex) {
            logger.warn("roleList XRobotException = {}", ex);
            ajaxResult = AjaxResult.error(ex.getErrorCode(), MessageUtils.message(messageSource, ex.getErrorCode()));
        } catch (Exception ex) {
            logger.error("roleList Exception = {}", ex);
            ajaxResult = AjaxResult.error(ErrorCode.SYS_FAIL, MessageUtils.message(messageSource, ErrorCode.SYS_FAIL));
        }
        return ajaxResult;
    }


}
