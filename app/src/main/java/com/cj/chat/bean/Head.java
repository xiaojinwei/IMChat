package com.cj.chat.bean;

/**
 * @author cj
 * @description: 消息头
 * @date :2019/6/5 22:01
 */
public class Head {
    private String msgId;
    private int msgType;
    private int msgContentType;
    private String fromId;
    private String toId;
    private long timestamp;
    private int statusReport;
    private String extend;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgContentType() {
        return msgContentType;
    }

    public void setMsgContentType(int msgContentType) {
        this.msgContentType = msgContentType;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusReport() {
        return statusReport;
    }

    public void setStatusReport(int statusReport) {
        this.statusReport = statusReport;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        return "Head{" +
                "msgId='" + msgId + '\'' +
                ", msgType=" + msgType +
                ", msgContentType=" + msgContentType +
                ", fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", timestamp=" + timestamp +
                ", statusReport=" + statusReport +
                ", extend='" + extend + '\'' +
                '}';
    }
}
