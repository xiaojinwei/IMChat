package com.cj.chat.im.handler;

import com.cj.chat.bean.AppMessage;
import com.cj.im.log.L;

/**
 * @author cj
 * @description: 群聊消息处理
 * @date :2019/6/7 16:28
 */
public class GroupChatMessageHandler extends AbstractMessageHandler {

    private static final String TAG = GroupChatMessageHandler.class.getSimpleName();

    @Override
    protected void action(AppMessage message) {
        L.d(TAG, "收到群聊消息，message=" + message);

    }
}
