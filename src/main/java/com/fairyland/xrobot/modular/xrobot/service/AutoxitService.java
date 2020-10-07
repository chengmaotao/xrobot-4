package com.fairyland.xrobot.modular.xrobot.service;

import com.fairyland.xrobot.modular.xrobot.autoxit.core.req.*;
import com.fairyland.xrobot.modular.xrobot.domain.Device;

public interface AutoxitService {

    Device checkClinetLogin(ClinetLoginReq paramReq);

    void modifyDeviceStateByClientStata(String id, String status);

    String clientGetTaskStatus(String id);

    String clientCheckPushMessageStatus(ClientCheckPushMessageReq businessParam);

    String clientSubmitPushJoinGroupsStatus(ClientSubmitPushJoinGroupsReq businessParam);

    String clientSubmitPushMessagesStatus(ClientSubmitPushMessagesReq businessParam);

    String clientSubmitCommentJoinGroupsStatus(ClientSubmitCommentJoinGroupsReq businessParam);

    String clientSubmitCommentsStatus(ClientSubmitCommentsReq businessParam);

    String clientSubmitCreateGroupsStatus(ClientSubmitCreateGroupsReq businessParam);

    String clientSubmitTaskResponseStatus(ClientSubmitTaskResponseReq businessParam);
}
