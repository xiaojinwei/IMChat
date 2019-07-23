package com.cj.im.listener;

/**
 * @author cj
 * @description: IMS连接状态回调
 * @date :2019/6/1 14:08
 */
public interface IMSConnectStatusCallback {

    /**
     * ims连接中
     */
    void onConnecting();

    /**
     * ims连接成功
     */
    void onConnected();

    /**
     * ims连接失败
     */
    void onConnectFailed();
}
