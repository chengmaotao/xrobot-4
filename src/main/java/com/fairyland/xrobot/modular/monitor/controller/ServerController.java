package com.fairyland.xrobot.modular.monitor.controller;

import com.fairyland.xrobot.framework.web.controller.BaseController;
import com.fairyland.xrobot.framework.web.domain.AjaxResult;
import com.fairyland.xrobot.framework.web.domain.Server;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public AjaxResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
