package com.cj.chat.im;

import com.cj.chat.bean.AppMessage;
import com.cj.chat.bean.BaseMessage;
import com.cj.chat.bean.ContentMessage;
import com.cj.chat.im.handler.IMessageHandler;
import com.cj.chat.im.handler.MessageHandlerFactory;
import com.cj.chat.utils.CThreadPoolExecutor;
import com.cj.im.log.L;

/**
 * @author cj
 * @description: 消息处理器
 * @date :2019/6/5 21:57
 */
public class MessageProcessor implements IMessageProcessor{

    private static final String TAG = MessageProcessor.class.getSimpleName();

    private MessageProcessor(){}

    private static class MessageProcessorInstance{
        private static final IMessageProcessor INSTANCE = new MessageProcessor();
    }

    public static IMessageProcessor getInstance(){
        return MessageProcessorInstance.INSTANCE;
    }

    /**
     * 接收消息
     * @param message
     */
    @Override
    public void receiveMsg(final AppMessage message) {
        CThreadPoolExecutor.runInBackground(new Runnable() {
            @Override
            public void run() {
                try {
                    IMessageHandler handler = MessageHandlerFactory.getHandlerByMessageType(message.getHead().getMsgType());
                    if (handler != null) {
                        handler.execute(message);
                    } else {
                        L.e(TAG, "未找到消息处理handler，msgType=" + message.getHead().getMsgType());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    L.e(TAG, "消息处理出错，reason=" + e.getMessage());
                }
            }
        });
    }

    /**
     * 发送消息
     * @param message
     */
    @Override
    public void sendMsg(final AppMessage message) {
        CThreadPoolExecutor.runInBackground(new Runnable() {
            @Override
            public void run() {
                boolean active = IMSClientBootstrap.getInstance().isActive();
                if (active) {
                    IMSClientBootstrap.getInstance().sendMessage(MessageBuilder.getProtoBufMessageBuilderByAppMessage(message).build());
                }else{
                    L.e(TAG, "发送消息失败");
                }
            }
        });
    }

    /**
     * 发送消息
     * @param message
     */
    @Override
    public void sendMsg(BaseMessage message) {
        this.sendMsg(MessageBuilder.buildAppMessage(message));
    }

    /**
     * 发送消息
     * @param message
     */
    @Override
    public void sendMsg(ContentMessage message) {
        this.sendMsg(MessageBuilder.buildAppMessage(message));
    }
}
