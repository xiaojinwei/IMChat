package com.cj.im;

import com.cj.im.listener.OnEventListener;
import com.cj.im.protobuf.MessageProtobuf;

/**
 * @author cj
 * @description: 消息转发器，负责将接收到的消息转发到应用层
 * @date :2019/6/1 14:44
 */
public class MsgDispatcher {
    private OnEventListener onEventListener;

    public MsgDispatcher() {

    }

    public void setOnEventListener(OnEventListener onEventListener) {
        this.onEventListener = onEventListener;
    }

    /**
     * 接收消息，并通过OnEventListener转发消息到应用层
     * @param msg
     */
    public void receivedMsg(MessageProtobuf.Msg msg) {
        if (onEventListener == null) return;
        onEventListener.dispatchMsg(msg);
    }
}
