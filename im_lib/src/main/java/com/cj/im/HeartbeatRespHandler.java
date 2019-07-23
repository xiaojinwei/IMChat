package com.cj.im;

import com.cj.im.log.L;
import com.cj.im.netty.NettyTcpClient;
import com.cj.im.protobuf.MessageProtobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author cj
 * @description: 心跳消息响应处理handler
 * @date :2019/6/1 21:42
 */
public class HeartbeatRespHandler extends ChannelInboundHandlerAdapter {
    private NettyTcpClient imsClient;

    public HeartbeatRespHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProtobuf.Msg heartbeatRespMsg = (MessageProtobuf.Msg) msg;
        if (heartbeatRespMsg == null || heartbeatRespMsg.getHead() == null) {
            return;
        }
        MessageProtobuf.Msg heartbeatMsg = imsClient.getHeartbeatMsg();
        if (heartbeatMsg == null || heartbeatMsg.getHead() == null) {
            return;
        }
        int heartbeatMsgType = heartbeatMsg.getHead().getMsgType();
        if (heartbeatMsgType == heartbeatRespMsg.getHead().getMsgType()) {
            L.d("收到服务端心跳响应消息，message=" + heartbeatRespMsg);
        }else{
            ctx.fireChannelRead(msg);
        }
    }
}
