package com.cj.chat.im;

import com.cj.chat.bean.AppMessage;
import com.cj.chat.bean.BaseMessage;
import com.cj.chat.bean.ContentMessage;

/**
 * @author cj
 * @description: 消息处理器接口
 * @date :2019/6/5 21:58
 */
public interface IMessageProcessor {

    /**
     * 接收消息
     * @param message
     */
    void receiveMsg(AppMessage message);

    /**
     * 发送消息
     * @param message
     */
    void sendMsg(AppMessage message);

    /**
     * 发送消息
     * @param message
     */
    void sendMsg(BaseMessage message);

    /**
     * 发送消息
     * @param message
     */
    void sendMsg(ContentMessage message);
}
