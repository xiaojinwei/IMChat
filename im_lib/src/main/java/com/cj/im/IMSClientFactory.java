package com.cj.im;

import com.cj.im.interf.IMSClientInterface;
import com.cj.im.netty.NettyTcpClient;

/**
 * @author cj
 * @description: ims实例工厂方法
 * @date :2019/6/1 14:58
 */
public class IMSClientFactory {

    public static IMSClientInterface getIMSClient() {
        return NettyTcpClient.getInstance();
    }

}
