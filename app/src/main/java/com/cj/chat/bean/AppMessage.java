package com.cj.chat.bean;

/**
 * @author cj
 * @description: App消息，用于把protobuf消息转换成app可用的消息类型
 * @date :2019/6/5 22:01
 */
public class AppMessage {
    private Head head;//消息头
    private String body;//消息体

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "AppMessage{" +
                "head=" + head +
                ", body='" + body + '\'' +
                '}';
    }
}
