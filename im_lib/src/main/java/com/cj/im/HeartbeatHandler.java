package com.cj.im;

import com.cj.im.log.L;
import com.cj.im.netty.NettyTcpClient;
import com.cj.im.protobuf.MessageProtobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author cj
 * @description: 心跳任务管理器
 * @date :2019/6/1 22:11
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient imsClient;

    public HeartbeatHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }

    private HeartbeatTask heartbeatTask;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state) {
                case READER_IDLE:
                    // 规定时间内没收到服务端心跳包响应，进行重连操作
                    imsClient.resetConnect(false);
                    break;
                case WRITER_IDLE:
                    // 规定时间内没向服务端发送心跳包，即发送一个心跳包
                    if (heartbeatTask == null) {
                        heartbeatTask = new HeartbeatTask(ctx);
                    }
                    imsClient.getLoopGroup().execWorkTask(heartbeatTask);
                    break;
            }
        }
    }

    private class HeartbeatTask implements Runnable{
        private ChannelHandlerContext ctx;

        public HeartbeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if (ctx.channel().isActive()) {
                MessageProtobuf.Msg heartbeatMsg = imsClient.getHeartbeatMsg();
                if (heartbeatMsg == null) {
                    return;
                }
                L.d("发送心跳消息，message=" + heartbeatMsg + "当前心跳间隔为：" + imsClient.getHeartbeatInterval() + "ms\n");
                imsClient.sendMsg(heartbeatMsg,false);
            }
        }
    }
}
