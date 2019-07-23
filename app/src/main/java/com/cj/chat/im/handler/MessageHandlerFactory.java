package com.cj.chat.im.handler;

import android.util.SparseArray;

import com.cj.chat.im.MessageType;

/**
 * @author cj
 * @description: 消息处理handler工厂
 * @date :2019/6/7 17:13
 */
public class MessageHandlerFactory {

    private MessageHandlerFactory(){}

    private static final SparseArray<IMessageHandler> HANDLERS = new SparseArray<>();

    static {
        /**
         * 单聊消息处理handler
         */
        HANDLERS.put(MessageType.SINGLE_CHAT.getMsgType(),new SingleChatMessageHandler());
        /**
         * 群聊消息处理handler
         */
        HANDLERS.put(MessageType.GROUP_CHAT.getMsgType(),new GroupChatMessageHandler());
        /**
         * 服务端返回的消息发送状态报告处理handler
         */
        HANDLERS.put(MessageType.SERVER_MSG_SENT_STATUS_REPORT.getMsgType(),new ServerReportMessageHandler());
    }

    /**
     * 根据消息类型获取对应的处理handler
     * @param msgType
     * @return
     */
    public static IMessageHandler getHandlerByMessageType(int msgType) {
        return HANDLERS.get(msgType);
    }
}
