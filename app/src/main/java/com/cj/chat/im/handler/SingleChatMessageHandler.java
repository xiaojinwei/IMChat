package com.cj.chat.im.handler;

import com.cj.chat.bean.AppMessage;
import com.cj.chat.bean.SingleMessage;
import com.cj.chat.event.CEventCenter;
import com.cj.chat.event.Events;
import com.cj.im.log.L;

/**
 * @author cj
 * @description: 单聊消息处理
 * @date :2019/6/7 14:24
 */
public class SingleChatMessageHandler extends AbstractMessageHandler {
    private static final String TAG = SingleChatMessageHandler.class.getSimpleName();

    @Override
    protected void action(AppMessage message) {
        L.d(TAG,"收到单聊消息，message=" + message);
        SingleMessage msg = new SingleMessage();
        msg.setMsgId(message.getHead().getMsgId());
        msg.setMsgType(message.getHead().getMsgType());
        msg.setMsgContentType(message.getHead().getMsgContentType());
        msg.setFromId(message.getHead().getFromId());
        msg.setToId(message.getHead().getToId());
        msg.setTimestamp(message.getHead().getTimestamp());
        msg.setExtend(message.getHead().getExtend());
        msg.setContent(message.getBody());

        //分发消息
        CEventCenter.dispatchEvent(Events.CHAT_SINGLE_MESSAGE,0,0,msg);
    }
}
