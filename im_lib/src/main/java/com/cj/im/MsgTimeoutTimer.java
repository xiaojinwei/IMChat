package com.cj.im;

import com.cj.im.common.IMSConfig;
import com.cj.im.interf.IMSClientInterface;
import com.cj.im.log.L;
import com.cj.im.protobuf.MessageProtobuf;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author cj
 * @description: 消息发送超时定时器，每一条消息对应一个定时器
 * @date :2019/6/1 15:54
 */
public class MsgTimeoutTimer extends Timer {
    private IMSClientInterface imsClient;//ims客户端
    private MessageProtobuf.Msg msg;//发送的消息
    private int currentResendCount;//当前重发次数
    private MsgTimeoutTask msgTask;//消息发送超时任务

    public MsgTimeoutTimer(IMSClientInterface imsClient, MessageProtobuf.Msg msg) {
        this.imsClient = imsClient;
        this.msg = msg;
        this.msgTask = new MsgTimeoutTask();
        this.schedule(msgTask,imsClient.getResendInterval(),imsClient.getResendInterval());
    }

    /**
     * 消息发送超时任务
     */
    private class MsgTimeoutTask extends TimerTask{

        @Override
        public void run() {
            if (imsClient.isClosed()) {
                if (imsClient.getMsgTimeoutTimerManager() != null) {
                    imsClient.getMsgTimeoutTimerManager().remove(msg.getHead().getMsgId());
                }
                return;
            }
            currentResendCount ++;
            if (currentResendCount > imsClient.getResendCount()) {
                //重发次数大于可重发次数，直接标识为发送失败
                try{
                    MessageProtobuf.Msg.Builder msgBuilder = MessageProtobuf.Msg.newBuilder();
                    MessageProtobuf.Head.Builder headBuild = MessageProtobuf.Head.newBuilder();
                    headBuild.setMsgId(msg.getHead().getMsgId());
                    headBuild.setMsgType(imsClient.getServerSentReportMsgType());
                    headBuild.setTimestamp(System.currentTimeMillis());
                    headBuild.setStatusReport(IMSConfig.DEFAULT_REPORT_SERVER_SEND_MSG_FAILURE);
                    msgBuilder.setHead(headBuild.build());
                    //通知应用层消息发送失败
                    imsClient.getMsgDispatcher().receivedMsg(msgBuilder.build());
                }finally {
                    //从消息发送超时管理器中移除该消息
                    imsClient.getMsgTimeoutTimerManager().remove(msg.getHead().getMsgId());
                    //执行到这里，认为连接已经断开或不稳定，触发重连
                    imsClient.resetConnect();
                    currentResendCount = 0;
                }
            }else{
                //发送消息，但不再加入超时管理器中，达到最大发送失败次数就算了
                sendMsg();
            }
        }
    }

    public void sendMsg() {
        L.d("正在重发消息，message=" + msg);
        imsClient.sendMsg(msg,false);
    }

    public MessageProtobuf.Msg getMsg() {
        return msg;
    }

    @Override
    public void cancel() {
        if (msgTask != null) {
            msgTask.cancel();
            msgTask = null;
        }
        super.cancel();
    }
}
