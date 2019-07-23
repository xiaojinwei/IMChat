package com.cj.chat.bean;

/**
 * @author cj
 * @description: 单聊消息
 * @date :2019/6/5 22:22
 */
public class SingleMessage extends ContentMessage {


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof SingleMessage)) {
            return false;
        }
        return super.equals(obj);
    }
}
