package com.fairyland.xrobot.modular.xrobot.service;

import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.ClinetLoginReq;
import com.fairyland.xrobot.modular.xrobot.domain.Device;

public interface AutoxitService {

    Device checkClinetLogin(ClinetLoginReq paramReq);
}
