package com.cj.chat.im;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cj.im.IMSClientFactory;
import com.cj.im.interf.IMSClientInterface;
import com.cj.im.listener.IMSConnectStatusCallback;
import com.cj.im.log.L;
import com.cj.im.protobuf.MessageProtobuf;

import java.util.Vector;

/**
 * @author cj
 * @description: 应用层的imsClient启动器
 * @date :2019/6/7 17:25
 */
public class IMSClientBootstrap {
    private static final String TAG = IMSClientBootstrap.class.getSimpleName();
    private static final IMSClientBootstrap INSTANCE = new IMSClientBootstrap();
    private IMSClientInterface imsClient;
    private boolean isActive;

    private IMSClientBootstrap(){}

    public static IMSClientBootstrap getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        String userId = "100001";
        String token = "token_" + userId;
        IMSClientBootstrap bootstrap = IMSClientBootstrap.getInstance();
        String hosts = "[{\"host\":\"127.0.0.1\", \"port\":8866}]";

    }

    public synchronized void init(String userId, String token, String hosts, int appStatus){
        if (!isActive) {
            Vector<String> serverUrlList = convertHosts(hosts);
            if (serverUrlList == null || serverUrlList.size() == 0) {
                L.e(TAG,"init IMLibClientBootstrap error,ims hosts is null");
                return;
            }
            isActive = true;
            L.d(TAG, "init IMLibClientBootstrap, servers=" + hosts);
            if (imsClient != null) {
                imsClient.close();
            }
            imsClient = IMSClientFactory.getIMSClient();
            updateAppStatus(appStatus);
            imsClient.init(serverUrlList, new IMSEventListener(userId, token), new IMSConnectStatusListener());
        }
    }

    /**
     * 发送消息
     * @param msg
     */
    public void sendMessage(MessageProtobuf.Msg msg) {
        if (isActive) {
            imsClient.sendMsg(msg);
        }
    }

    private void updateAppStatus(int appStatus) {
        if (imsClient == null) {
            return;
        }
        imsClient.setAppStatus(appStatus);
    }

    public boolean isActive() {
        return isActive;
    }

    private Vector<String> convertHosts(String hosts) {
        if (hosts != null && hosts.length() > 0) {
            JSONArray hostArray = JSONArray.parseArray(hosts);
            if (null != hostArray && hostArray.size() > 0) {
                Vector<String> serverUrlList = new Vector<String>();
                JSONObject host;
                for (int i = 0; i < hostArray.size(); i++) {
                    host = JSON.parseObject(hostArray.get(i).toString());
                    serverUrlList.add(host.getString("host") + " "
                            + host.getInteger("port"));
                }
                return serverUrlList;
            }
        }
        return null;
    }
}
