package com.cj.im.netty;

import com.cj.im.ExecutorServiceFactory;
import com.cj.im.HeartbeatHandler;
import com.cj.im.MsgDispatcher;
import com.cj.im.MsgTimeoutTimerManager;
import com.cj.im.common.IMSConfig;
import com.cj.im.interf.IMSClientInterface;
import com.cj.im.listener.IMSConnectStatusCallback;
import com.cj.im.listener.OnEventListener;
import com.cj.im.log.L;
import com.cj.im.protobuf.MessageProtobuf;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.StringUtil;

/**
 * @author cj
 * @description: 基于netty实现的tcp ims
 * @date :2019/6/1 14:55
 */
public class NettyTcpClient implements IMSClientInterface {

    private static volatile NettyTcpClient instance;
    private boolean isClosed = false;//标识ims是否已经关闭
    private Vector<String> serverUrlList;//ims服务器地址
    private OnEventListener onEventListener;//与应用层交互的listener
    private IMSConnectStatusCallback imsConnectStatusCallback;//ims连接状态回调监听器
    private MsgDispatcher msgDispatcher;//消息转发器
    private ExecutorServiceFactory loopGroup;//线程池工厂
    private MsgTimeoutTimerManager msgTimeoutTimerManager;//消息发送超时定时器管理

    private Bootstrap bootstrap;
    private Channel channel;

    private boolean isReconnecting = false;//是否正在进行重连
    private int connectStatus = IMSConfig.CONNECT_STATE_FAILURE;// ims连接状态，初始化为连接失败
    private int reconnectInterval = IMSConfig.DEFAULT_RECONNECT_BASE_DELAY_TIME;// 重连间隔时长
    private int connectTimeout = IMSConfig.DEFAULT_CONNECT_TIMEOUT; // 连接超时时长
    private int heartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;// 心跳间隔时间
    private int foregroundHeartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;// 应用在前台时心跳间隔时间
    private int backgroundHeartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_BACKGROUND;// 应用在后台时心跳间隔时间
    private int appStatus = IMSConfig.APP_STATUS_FOREGROUND; // app前后台状态
    private int resendCount = IMSConfig.DEFAULT_RESEND_COUNT;// 消息发送超时重发次数
    private int resendInterval = IMSConfig.DEFAULT_RESEND_INTERVAL; // 消息发送失败重发间隔时长

    private String currentHost = null;// 当前连接host
    private int currentPort = -1;// 当前连接port

    public static IMSClientInterface getInstance() {
        if (instance == null) {
            synchronized (NettyTcpClient.class) {
                if (instance == null) {
                    instance = new NettyTcpClient();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     * @param serverUrlList 服务器地址列表
     * @param listener      与应用层交互的listener
     * @param callback      ims连接状态回调
     */
    @Override
    public void init(Vector<String> serverUrlList, OnEventListener listener, IMSConnectStatusCallback callback) {
        close();
        this.isClosed = false;
        this.serverUrlList = serverUrlList;
        this.onEventListener = listener;
        this.imsConnectStatusCallback = callback;
        this.msgDispatcher = new MsgDispatcher();
        this.msgDispatcher.setOnEventListener(listener);
        loopGroup  = new ExecutorServiceFactory();
        loopGroup.initBossLoopGroup();//初始化重连线程组
        msgTimeoutTimerManager = new MsgTimeoutTimerManager(this);
        resetConnect(true);//进行第一次连接
    }

    /**
     * 重置ims连接
     * 重置连接
     * 首次连接也可以认为是重连
     */
    @Override
    public void resetConnect() {
        resetConnect(false);
    }

    /**
     * 重置ims连接
     * 重置连接
     * 重载方法
     * @param isFirst 是否首次连接
     */
    @Override
    public void resetConnect(boolean isFirst) {
        if (!isFirst) {
            try {
                Thread.sleep(IMSConfig.DEFAULT_RECONNECT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //只有第一个调用者才能赋值并调用重连
        if (!isClosed && !isReconnecting) {
            synchronized (this) {
                if (!isClosed && !isReconnecting) {
                    //标识正在进行重连
                    isReconnecting = true;
                    //回调ims连接状态
                    onConnectStatusCallback(IMSConfig.CONNECT_STATE_CONNECTING);
                    //先关闭channel
                    closeChannel();
                    //执行重连任务
                    loopGroup.execBossTask(new ResetConnectRunnable(isFirst));
                }
            }
        }
    }

    /**
     * 关闭channel
     */
    private void closeChannel() {
        try {
            if (channel != null) {
                channel.close();
                channel.eventLoop().shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
            L.e("关闭channel出错，reason:" + e.getMessage());
        }finally {
            channel = null;
        }
    }

    /**
     * 回调ims连接状态
     * @param connectStatus
     */
    private void onConnectStatusCallback(int connectStatus) {
        this.connectStatus = connectStatus;
        switch (connectStatus) {
            case IMSConfig.CONNECT_STATE_CONNECTING:
                L.d("ims连接中...");
                if (imsConnectStatusCallback != null) {
                    imsConnectStatusCallback.onConnecting();
                }
                break;
            case IMSConfig.CONNECT_STATE_SUCCESSFUL:
                L.d("ims连接成功，host『%s』, port『%s』", currentHost, currentPort);
                if (imsConnectStatusCallback != null) {
                    imsConnectStatusCallback.onConnected();
                }
                //连接成功，发送握手消息
                MessageProtobuf.Msg handshakeMsg = getHandshakeMsg();
                if (handshakeMsg != null) {
                    L.d("发送握手消息，message=" + handshakeMsg);
                    sendMsg(handshakeMsg,false);
                }else{
                    L.e("请应用层构建握手消息！");
                }
                break;
            case IMSConfig.CONNECT_STATE_FAILURE:
                L.d("ims连接失败");
                if (imsConnectStatusCallback != null) {
                    imsConnectStatusCallback.onConnectFailed();
                }
                break;
        }
    }

    @Override
    public void close() {
        if (isClosed) {
            return;
        }
        isClosed = true;
        //关闭channel
        try {
            closeChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭bootstrap
        try {
            if (bootstrap != null) {
                bootstrap.group().shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //释放线程池
        try {
            if (loopGroup != null) {
                loopGroup.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverUrlList != null) {
                    serverUrlList.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            isReconnecting = false;
            channel = null;
            bootstrap = null;
        }
    }

    /**
     * 标识ims是否已关闭
     * @return
     */
    @Override
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * 发送消息
     * @param msg
     */
    @Override
    public void sendMsg(MessageProtobuf.Msg msg) {
        sendMsg(msg,true);
    }

    /**
     * 发送消息
     * @param msg
     * @param isJoinTimeoutManager 是否加入发送超时管理器
     */
    @Override
    public void sendMsg(MessageProtobuf.Msg msg, boolean isJoinTimeoutManager) {
        if (msg == null || msg.getHead() == null) {
            L.e("发送消息失败，消息为空\tmessage=" + msg);
            return;
        }
        if (!StringUtil.isNullOrEmpty(msg.getHead().getMsgId())) {
            if (isJoinTimeoutManager) {
                msgTimeoutTimerManager.add(msg);
            }
        }
        if (channel == null) {
            L.e("发送消息失败，channel为空\tmessage=" + msg);
        }
        //发送
        try {
            channel.writeAndFlush(msg);
        } catch (Exception e) {
            e.printStackTrace();
            L.e("发送消息失败，reason:" + e.getMessage() + "\tmessage=" + msg);
        }
    }

    /**
     * 获取重连间隔时长
     * @return
     */
    @Override
    public int getReconnectInterval() {
        if (onEventListener != null && onEventListener.getReconnectInterval() > 0) {
            return reconnectInterval = onEventListener.getReconnectInterval();
        }
        return reconnectInterval;
    }

    /**
     * 获取连接超时时长
     * @return
     */
    @Override
    public int getConnectTimeout() {
        if (onEventListener != null && onEventListener.getConnectTimeout() > 0) {
            return connectTimeout = onEventListener.getConnectTimeout();
        }
        return connectTimeout;
    }

    /**
     * 获取应用在前台时心跳间隔时间
     * @return
     */
    @Override
    public int getForegroundHeartbeatInterval() {
        if (onEventListener != null && onEventListener.getForegroundHeartbeatInterval() > 0) {
            return foregroundHeartbeatInterval = onEventListener.getForegroundHeartbeatInterval();
        }
        return foregroundHeartbeatInterval;
    }

    /**
     * 获取应用在后台台时心跳间隔时间
     * @return
     */
    @Override
    public int getBackgroundHeartbeatInterval() {
        if (onEventListener != null && onEventListener.getBackgroundHeartbeatInterval() > 0) {
            return backgroundHeartbeatInterval = onEventListener.getBackgroundHeartbeatInterval();
        }
        return backgroundHeartbeatInterval;
    }

    /**
     * 设置app前后台状态
     * @param appStatus
     */
    @Override
    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
        if (this.appStatus == IMSConfig.APP_STATUS_FOREGROUND) {
            heartbeatInterval = foregroundHeartbeatInterval;
        } else if (this.appStatus == IMSConfig.APP_STATUS_BACKGROUND) {
            heartbeatInterval = backgroundHeartbeatInterval;
        }
        addHeartbeatHandler();
    }

    /**
     * 获取由应用层构造的握手消息
     * @return
     */
    @Override
    public MessageProtobuf.Msg getHandshakeMsg() {
        if (onEventListener != null) {
            return onEventListener.getHandshakeMsg();
        }
        return null;
    }

    /**
     * 获取由应用层构造的心跳消息
     * @return
     */
    @Override
    public MessageProtobuf.Msg getHeartbeatMsg() {
        if (onEventListener != null) {
            return onEventListener.getHeartbeatMsg();
        }
        return null;
    }

    /**
     * 获取应用层消息发送状态报告消息类型
     * @return
     */
    @Override
    public int getServerSentReportMsgType() {
        if (onEventListener != null) {
            return onEventListener.getServerSentReportMsgType();
        }
        return 0;
    }

    /**
     * 获取应用层消息接收状态报告消息类型
     * @return
     */
    @Override
    public int getClientReceivedReportMsgType() {
        if (onEventListener != null) {
            return onEventListener.getClientReceivedReportMsgType();
        }
        return 0;
    }

    /**
     * 获取应用层消息发送超时重发次数
     * @return
     */
    @Override
    public int getResendCount() {
        if (onEventListener != null && onEventListener.getResendCount() != 0) {
            return resendCount = onEventListener.getResendCount();
        }
        return resendCount;
    }

    /**
     * 获取应用层消息发送超时重发间隔
     * @return
     */
    @Override
    public int getResendInterval() {
        if (onEventListener != null && onEventListener.getResendInterval() != 0) {
            return resendInterval = onEventListener.getResendInterval();
        }
        return resendInterval;
    }

    /**
     * 获取消息转发器
     * @return
     */
    @Override
    public MsgDispatcher getMsgDispatcher() {
        return msgDispatcher;
    }

    /**
     * 获取消息发送超时定时器
     * @return
     */
    @Override
    public MsgTimeoutTimerManager getMsgTimeoutTimerManager() {
        return msgTimeoutTimerManager;
    }

    /**
     * 获取线程池
     *
     * @return
     */
    public ExecutorServiceFactory getLoopGroup() {
        return loopGroup;
    }

    /**
     * 移除指定handler
     * @param handlerName
     */
    private void removeHandler(String handlerName) {
        try {
            if (channel != null && channel.pipeline().get(handlerName) != null) {
                channel.pipeline().remove(handlerName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            L.e("移除handler失败，handlerName=" + handlerName);
        }
    }

    /**
     *  添加心跳消息管理handler
     */
    public void addHeartbeatHandler() {
        if (channel == null || !channel.isActive() || channel.pipeline() == null) {
            return;
        }
        try {
            // 之前存在的读写超时handler，先移除掉，再重新添加
            if (channel.pipeline().get(IdleStateHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(IdleStateHandler.class.getSimpleName());
            }
            // 3次心跳没响应，代表连接已断开
            channel.pipeline().addFirst(IdleStateHandler.class.getSimpleName(),new IdleStateHandler(
                    heartbeatInterval * 3,heartbeatInterval,0, TimeUnit.MILLISECONDS
            ));
            // 重新添加HeartbeatHandler
            if (channel.pipeline().get(HeartbeatHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(HeartbeatHandler.class.getSimpleName());
            }
            if (channel.pipeline().get(TCPReadHandler.class.getSimpleName()) != null) {
                channel.pipeline().addBefore(TCPReadHandler.class.getSimpleName(),HeartbeatHandler.class.getSimpleName(),new HeartbeatHandler(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
            L.e("添加心跳消息管理handler失败，reason：" + e.getMessage());
        }
    }

    /**
     * 初始化bootstrap
     */
    private void initBootstrap() {
        EventLoopGroup loopGroup = new NioEventLoopGroup(4);
        bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).channel(NioSocketChannel.class);
        // 设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        // 设置禁用nagle算法
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        // 设置连接超时时长
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getConnectTimeout());
        //设置初始化Channel
        bootstrap.handler(new TCPChannelInitializerHandler(this));
    }

    /**
     * 从应用层获取网络是否可用
     * @return
     */
    private boolean isNetworkAvailable() {
        if (onEventListener != null) {
            return onEventListener.isNetworkAvailable();
        }
        return false;
    }

    /**
     * 获取心跳间隔时间
     * @return
     */
    public int getHeartbeatInterval() {
        return this.heartbeatInterval;
    }

    /**
     * 真正连接服务器的地方
     */
    private void toServer() {
        try {
            channel = bootstrap.connect(currentHost,currentPort).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            L.e("连接Server(ip[%s], port[%s])失败", currentHost, currentPort);
            channel = null;
        }
    }


    /**
     * 重连任务
     */
    private class ResetConnectRunnable implements Runnable{
        private boolean isFirst;

        public ResetConnectRunnable(boolean isFirst) {
            this.isFirst = isFirst;
        }

        @Override
        public void run() {
            // 非首次进行重连，执行到这里即代表已经连接失败，回调连接状态到应用层
            if (!isFirst) {
                onConnectStatusCallback(IMSConfig.CONNECT_STATE_FAILURE);
            }
            try {
                // 重连时，释放工作线程组，也就是停止心跳
                loopGroup.destroyWorkLoopGroup();
                while (!isClosed){
                    //网络不可用时，等待两秒
                    if (!isNetworkAvailable()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    //网络可用才进行连接
                    int status;
                    if ((status = reConnect()) == IMSConfig.CONNECT_STATE_SUCCESSFUL) {
                        onConnectStatusCallback(status);
                        // 连接成功，跳出循环
                        break;
                    }

                    if (status == IMSConfig.CONNECT_STATE_FAILURE) {
                        onConnectStatusCallback(status);
                        try {
                            Thread.sleep(IMSConfig.DEFAULT_RECONNECT_INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                // 标识重连任务停止
                isReconnecting = false;
            }
        }

        /**
         * 重连，首次连接也认为时第一次重连
         * @return
         */
        private int reConnect() {
            //未关闭才去连接
            if (!isClosed) {
                try{
                    //先释放EventLoop线程组
                    if (bootstrap != null) {
                        bootstrap.group().shutdownGracefully();
                    }
                }finally {
                    bootstrap = null;
                }
                //初始化bootstrap
                initBootstrap();
                return connectServer();
            }
            return IMSConfig.CONNECT_STATE_FAILURE;
        }

        /**
         * 连接服务器
         * @return
         */
        private int connectServer() {
            // 如果服务器地址无效，直接回调连接状态，不再进行连接
            // 有效的服务器地址示例：127.0.0.1 8860
            if (serverUrlList == null || serverUrlList.size() == 0) {
                return IMSConfig.CONNECT_STATE_FAILURE;
            }
            for (int i = 0; (!isClosed && i < serverUrlList.size()); i++) {
                String serverUrl = serverUrlList.get(i);
                // 如果服务器地址无效，直接回调连接状态，不再进行连接
                if (StringUtil.isNullOrEmpty(serverUrl)) {
                    return IMSConfig.CONNECT_STATE_FAILURE;
                }
                String[] address_port = serverUrl.split(" ");
                for (int j = 0; j < IMSConfig.DEFAULT_RECONNECT_COUNT; j++) {
                    // 如果ims已关闭，或网络不可用，直接回调连接状态，不再进行连接
                    if (isClosed || !isNetworkAvailable()) {
                        return IMSConfig.CONNECT_STATE_FAILURE;
                    }
                    // 回调连接状态
                    if (connectStatus != IMSConfig.CONNECT_STATE_CONNECTING) {
                        onConnectStatusCallback(IMSConfig.CONNECT_STATE_CONNECTING);
                    }
                    L.d("正在进行『%s』的第『%d』次连接，当前重连延时时长为『%dms』", serverUrl, j, j * getReconnectInterval());

                    try {
                        currentHost = address_port[0];//获取host
                        currentPort = Integer.parseInt(address_port[1]);//获取port
                        toServer();// 连接服务器

                        // channel不为空，即认为连接已成功
                        if (channel != null) {
                            return IMSConfig.CONNECT_STATE_SUCCESSFUL;
                        }else{
                            // 连接失败，则线程休眠n * 重连间隔时长
                            Thread.sleep(j * getReconnectInterval());
                        }
                    } catch (Exception e) {
                        close();
                        // 线程被中断，则强制关闭
                        break;
                    }
                }
            }
            // 执行到这里，代表连接失败
            return IMSConfig.CONNECT_STATE_FAILURE;
        }
    }



}
