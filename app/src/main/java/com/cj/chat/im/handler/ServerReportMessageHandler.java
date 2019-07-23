package com.cj.chat.im.handler;

import com.cj.chat.bean.AppMessage;
import com.cj.im.log.L;

/**
 * @author cj
 * @description: 服务端返回的消息发送状态报告
 * @date :2019/6/7 17:12
 */
public class ServerReportMessageHandler extends AbstractMessageHandler{

    private static final String TAG = ServerReportMessageHandler.class.getSimpleName();

    @Override
    protected void action(AppMessage message) {
        L.d(TAG, "收到消息状态报告，message=" + message);
    }
}
