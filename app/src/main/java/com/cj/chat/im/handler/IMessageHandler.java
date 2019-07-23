package com.cj.chat.im.handler;

import com.cj.chat.bean.AppMessage;

/**
 * @author cj
 * @description: 消息处理
 * @date :2019/6/7 14:18
 */
public interface IMessageHandler {

    void execute(AppMessage message);

}
