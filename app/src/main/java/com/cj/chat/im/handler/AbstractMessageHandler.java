package com.cj.chat.im.handler;

import com.cj.chat.bean.AppMessage;

/**
 * @author cj
 * @description:
 * @date :2019/6/7 14:19
 */
public abstract class AbstractMessageHandler implements IMessageHandler{
    @Override
    public void execute(AppMessage message) {
        action(message);
    }

    protected abstract void action(AppMessage message);
}
