package com.cj.im;

import com.cj.im.interf.IMSClientInterface;
import com.cj.im.log.L;
import com.cj.im.protobuf.MessageProtobuf;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.util.internal.StringUtil;

/**
 * @author cj
 * @description: 消息发送超时管理器，用于管理消息定时器的新增、移除等
 * @date :2019/6/1 14:50
 */
public class MsgTimeoutTimerManager {

    private Map<String,MsgTimeoutTimer> msgTimeoutMap = new ConcurrentHashMap<>();
    private IMSClientInterface imsClient;//ims客户端

    public MsgTimeoutTimerManager(IMSClientInterface imsClient) {
        this.imsClient = imsClient;
    }

    /**
     * 添加消息发送超时管理器
     * @param msg
     */
    public void add(MessageProtobuf.Msg msg) {
        if (msg == null || msg.getHead() == null) {
            return;
        }
        int handshakeMsgType = -1;
        int heartbeatMsgType = -1;
        int clientReceivedReportMsgType = imsClient.getClientReceivedReportMsgType();
        MessageProtobuf.Msg handshakeMsg = imsClient.getHandshakeMsg();
        if (handshakeMsg != null && handshakeMsg.getHead() != null) {
            handshakeMsgType = handshakeMsg.getHead().getMsgType();
        }
        MessageProtobuf.Msg heartbeatMsg = imsClient.getHeartbeatMsg();
        if (heartbeatMsg != null && heartbeatMsg.getHead() != null) {
            heartbeatMsgType = heartbeatMsg.getHead().getMsgType();
        }
        int msgType = msg.getHead().getMsgType();
        //握手消息，心跳消息，客户端返回的状态报告消息，不用重发
        if (msgType == handshakeMsgType || msgType == heartbeatMsgType || msgType == clientReceivedReportMsgType) {
            return;
        }
        String msgId = msg.getHead().getMsgId();
        if (!msgTimeoutMap.containsKey(msgId)) {
            MsgTimeoutTimer msgTimeoutTimer = new MsgTimeoutTimer(imsClient,msg);
            msgTimeoutMap.put(msgId, msgTimeoutTimer);
        }
        L.d("添加消息超发送超时管理器，message=" + msg + "\t当前管理器消息数：" + msgTimeoutMap.size());
    }

    /**
     * 从发送超时管理器中移除消息，并停止定时器
     * @param msgId
     */
    public void remove(String msgId) {
        if (StringUtil.isNullOrEmpty(msgId)) {
            return;
        }
        MsgTimeoutTimer msgTimer = msgTimeoutMap.remove(msgId);
        MessageProtobuf.Msg msg = null;
        if (msgTimer != null) {
            msg = msgTimer.getMsg();
            msgTimer.cancel();
            msgTimer = null;
        }
        L.d("从发送消息管理器移除消息，message=" + msg);
    }

    /**
     * 重连成功回调，重连并握手成功时，重发消息发送超时管理器中所有的消息
     */
    public synchronized void onResetConnected() {
        Iterator<Map.Entry<String, MsgTimeoutTimer>> iterator = msgTimeoutMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MsgTimeoutTimer> next = iterator.next();
            next.getValue().sendMsg();
        }
    }
}
