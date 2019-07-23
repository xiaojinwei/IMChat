package com.cj.chat.event;

/**
 * @author cj
 * @description: 事件监听器
 * @date :2019/6/7 15:44
 */
public interface I_CEventListener {

    /**
     * 事件回调函数
     * 注意：
     * 如果 obj 使用了对象池（如 socket 事件的对象）
     * 那么事件完成后，obj 即自动回收到对象池，请不要在其它线程继续使用，否则可能会导致数据不正常
     * @param topic
     * @param msgCode
     * @param resultCode
     * @param obj
     */
    void onCEvent(String topic, int msgCode, int resultCode, Object obj);
}
