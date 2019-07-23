package com.cj.im.netty;

import com.alibaba.fastjson.JSONObject;
import com.cj.im.common.IMSConfig;
import com.cj.im.log.L;
import com.cj.im.protobuf.MessageProtobuf;

import java.util.UUID;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;

/**
 * @author cj
 * @description: 消息接收处理handler
 * @date :2019/6/1 22:21
 */
public class TCPReadHandler extends ChannelInboundHandlerAdapter {
    private NettyTcpClient imsClient;

    public TCPReadHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        L.d("TCPReadHandler channelInactive()");
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }
        //触发重连
        imsClient.resetConnect(false);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        L.e("TCPReadHandler exceptionCaught()");
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }
        //触发重连
        imsClient.resetConnect(false);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProtobuf.Msg message = (MessageProtobuf.Msg) msg;
        if (message == null || message.getHead() == null) {
            return;
        }
        int msgType = message.getHead().getMsgType();
        if (msgType == imsClient.getServerSentReportMsgType()) {
            int statusReport = message.getHead().getStatusReport();
            L.d("服务端状态报告：「%d」, 1代表成功，0代表失败", statusReport);
            if (statusReport == IMSConfig.DEFAULT_REPORT_SERVER_SEND_MSG_SUCCESSFUL) {
                L.d("收到服务端消息发送状态报告，message=" + message + "，从超时管理器移除");
                imsClient.getMsgTimeoutTimerManager().remove(message.getHead().getMsgId());
            }
        }else{
            // 其它消息
            // 收到消息后，立马给服务端回一条消息接收状态报告
            L.d("收到消息，message=" + message);
            MessageProtobuf.Msg receivedReportMsg = buildReceivedReportMsg(message.getHead().getMsgId());
            if (receivedReportMsg != null) {
                imsClient.sendMsg(receivedReportMsg);
            }
        }
        // 接收消息，由消息转发器转发到应用层
        imsClient.getMsgDispatcher().receivedMsg(message);
    }

    private MessageProtobuf.Msg buildReceivedReportMsg(String msgId) {
        if (StringUtil.isNullOrEmpty(msgId)) {
            return null;
        }
        MessageProtobuf.Msg.Builder msgBuilder = MessageProtobuf.Msg.newBuilder();
        MessageProtobuf.Head.Builder headBuilder = MessageProtobuf.Head.newBuilder();
        headBuilder.setMsgId(UUID.randomUUID().toString());
        headBuilder.setMsgType(imsClient.getClientReceivedReportMsgType());
        headBuilder.setTimestamp(System.currentTimeMillis());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msgId", msgId);
        headBuilder.setExtend(jsonObject.toString());
        msgBuilder.setHead(headBuilder.build());
        return msgBuilder.build();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }
}
