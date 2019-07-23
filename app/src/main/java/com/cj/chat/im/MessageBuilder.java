package com.cj.chat.im;

import com.cj.chat.bean.AppMessage;
import com.cj.chat.bean.BaseMessage;
import com.cj.chat.bean.ContentMessage;
import com.cj.chat.bean.Head;
import com.cj.chat.utils.StringUtil;
import com.cj.im.protobuf.MessageProtobuf;

/**
 * @author cj
 * @description: 消息转换
 * @date :2019/6/7 14:33
 */
public class MessageBuilder {

    /**
     * 根据聊天消息，生成一条可以能够传输通讯的消息
     * @param msgId
     * @param type
     * @param subType
     * @param fromId
     * @param toId
     * @param extend
     * @param content
     * @return
     */
    public static AppMessage buildAppMessage(String msgId, int type, int subType, String fromId,
                                             String toId, String extend, String content){
        AppMessage message = new AppMessage();
        Head head = new Head();
        head.setMsgId(msgId);
        head.setMsgType(type);
        head.setMsgContentType(subType);
        head.setFromId(fromId);
        head.setToId(toId);
        head.setExtend(extend);
        message.setHead(head);
        message.setBody(content);
        return message;
    }

    /**
     * 根据聊天消息，生成一条可以能够传输通讯的消息
     *
     * @param msg
     * @return
     */
    public static AppMessage buildAppMessage(ContentMessage msg) {
        AppMessage message = new AppMessage();
        Head head = new Head();
        head.setMsgId(msg.getMsgId());
        head.setMsgType(msg.getMsgType());
        head.setMsgContentType(msg.getMsgContentType());
        head.setFromId(msg.getFromId());
        head.setToId(msg.getToId());
        head.setTimestamp(msg.getTimestamp());
        head.setExtend(msg.getExtend());
        message.setHead(head);
        message.setBody(msg.getContent());
        return message;
    }

    /**
     * 根据聊天消息，生成一条可以能够传输通讯的消息
     *
     * @param msg
     * @return
     */
    public static AppMessage buildAppMessage(BaseMessage msg) {
        AppMessage message = new AppMessage();
        Head head = new Head();
        head.setMsgId(msg.getMsgId());
        head.setMsgType(msg.getMsgType());
        head.setMsgContentType(msg.getMsgContentType());
        head.setFromId(msg.getFromId());
        head.setToId(msg.getToId());
        head.setExtend(msg.getExtend());
        head.setTimestamp(msg.getTimestamp());
        message.setHead(head);
        message.setBody(msg.getContent());
        return message;
    }

    /**
     * 根据业务消息对象获取protoBuf消息对应的builder
     * @param message
     * @return
     */
    public static MessageProtobuf.Msg.Builder getProtoBufMessageBuilderByAppMessage(AppMessage message) {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        MessageProtobuf.Head.Builder headerBuilder = MessageProtobuf.Head.newBuilder();
        headerBuilder.setMsgType(message.getHead().getMsgType());
        headerBuilder.setStatusReport(message.getHead().getStatusReport());
        headerBuilder.setMsgContentType(message.getHead().getMsgContentType());
        if (!StringUtil.isEmpty(message.getHead().getMsgId())) {
            headerBuilder.setMsgId(message.getHead().getMsgId());
        }
        if (!StringUtil.isEmpty(message.getHead().getFromId())) {
            headerBuilder.setFromId(message.getHead().getFromId());
        }
        if (!StringUtil.isEmpty(message.getHead().getToId())) {
            headerBuilder.setToId(message.getHead().getToId());
        }
        if (message.getHead().getTimestamp() != 0) {
            headerBuilder.setTimestamp(message.getHead().getTimestamp());
        }
        if (!StringUtil.isEmpty(message.getHead().getExtend())) {
            headerBuilder.setExtend(message.getHead().getExtend());
        }
        if (!StringUtil.isEmpty(message.getBody())) {
            builder.setBody(message.getBody());
        }
        builder.setHead(headerBuilder);
        return builder;
    }

    /**
     * 通过protobuf消息对象获取业务消息对象
     * @param protobufMessage
     * @return
     */
    public static AppMessage getMessageByProtobuf(MessageProtobuf.Msg protobufMessage) {
        AppMessage message = new AppMessage();
        Head head = new Head();
        MessageProtobuf.Head protoHead = protobufMessage.getHead();
        head.setMsgId(protoHead.getMsgId());
        head.setMsgType(protoHead.getMsgType());
        head.setStatusReport(protoHead.getStatusReport());
        head.setMsgContentType(protoHead.getMsgContentType());
        head.setFromId(protoHead.getFromId());
        head.setToId(protoHead.getToId());
        head.setTimestamp(protoHead.getTimestamp());
        head.setExtend(protoHead.getExtend());
        message.setHead(head);
        message.setBody(protobufMessage.getBody());
        return message;
    }
}
