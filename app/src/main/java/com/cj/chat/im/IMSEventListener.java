package com.cj.chat.im;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alibaba.fastjson.JSONObject;
import com.cj.chat.ChatApp;
import com.cj.im.listener.OnEventListener;
import com.cj.im.protobuf.MessageProtobuf;

import java.util.UUID;

/**
 * @author cj
 * @description: 与ims交互的listener
 * @date :2019/6/5 22:47
 */
public class IMSEventListener implements OnEventListener {

    private String userId;
    private String token;

    public IMSEventListener(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    /**
     * 接收ims转发过来的消息
     * @param msg
     */
    @Override
    public void dispatchMsg(MessageProtobuf.Msg msg) {
        MessageProcessor.getInstance().receiveMsg(MessageBuilder.getMessageByProtobuf(msg));
    }

    /**
     * 网络是否可用
     * @return
     */
    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) ChatApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 设置ims重连间隔时长，0表示默认使用ims的值
     * @return
     */
    @Override
    public int getReconnectInterval() {
        return 0;
    }

    /**
     * 设置ims连接超时时长，0表示默认使用ims的值
     * @return
     */
    @Override
    public int getConnectTimeout() {
        return 0;
    }

    /**
     * 设置应用在前台时ims心跳间隔时长，0表示默认使用ims的值
     * @return
     */
    @Override
    public int getForegroundHeartbeatInterval() {
        return 0;
    }

    /**
     * 设置应用在后台时ims心跳间隔时长，0表示默认使用ims的值
     * @return
     */
    @Override
    public int getBackgroundHeartbeatInterval() {
        return 0;
    }

    /**
     * 构建握手消息
     * @return
     */
    @Override
    public MessageProtobuf.Msg getHandshakeMsg() {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        MessageProtobuf.Head.Builder headBuilder = MessageProtobuf.Head.newBuilder();
        headBuilder.setMsgId(UUID.randomUUID().toString());
        headBuilder.setMsgType(MessageType.HANDSHAKE.getMsgType());
        headBuilder.setFromId(userId);
        headBuilder.setTimestamp(System.currentTimeMillis());
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("token", token);
        headBuilder.setExtend(jsonObj.toString());
        builder.setHead(headBuilder.build());
        return builder.build();
    }

    @Override
    public MessageProtobuf.Msg getHeartbeatMsg() {
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        MessageProtobuf.Head.Builder headBuilder = MessageProtobuf.Head.newBuilder();
        headBuilder.setMsgId(UUID.randomUUID().toString());
        headBuilder.setMsgType(MessageType.HEARTBEAT.getMsgType());
        headBuilder.setFromId(userId);
        headBuilder.setTimestamp(System.currentTimeMillis());
        builder.setHead(headBuilder.build());
        return builder.build();
    }

    /**
     * 服务端返回的消息发送状态报告消息类型
     * @return
     */
    @Override
    public int getServerSentReportMsgType() {
        return MessageType.SERVER_MSG_SENT_STATUS_REPORT.getMsgType();
    }

    /**
     * 客户端提交的消息接收状态报告消息类型
     * @return
     */
    @Override
    public int getClientReceivedReportMsgType() {
        return MessageType.CLIENT_MSG_RECEIVED_STATUS_REPORT.getMsgType();
    }

    /**
     * 设置ims消息发送超时重发次数，0表示默认使用ims的值
     * @return
     */
    @Override
    public int getResendCount() {
        return 0;
    }

    /**
     * 设置ims消息发送超时重发间隔时长，0表示默认使用ims的值
     * @return
     */
    @Override
    public int getResendInterval() {
        return 0;
    }
}
