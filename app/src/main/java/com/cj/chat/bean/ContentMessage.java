package com.cj.chat.bean;

import com.cj.chat.utils.StringUtil;

/**
 * @author cj
 * @description: 内容消息，包含单聊消息及群聊消息
 * @date :2019/6/5 22:17
 */
public class ContentMessage extends BaseMessage {
    protected boolean isRead;
    protected boolean isPlaying;
    protected boolean isLoading;

    public ContentMessage() {
    }

    public ContentMessage(String msgId, int msgType, int msgContentType, String fromId, String toId,
                          long timestamp, int statusReport, String extend, String content) {
        this.msgId = msgId;
        this.msgType = msgType;
        this.msgContentType = msgContentType;
        this.fromId = fromId;
        this.toId = toId;
        this.timestamp = timestamp;
        this.statusReport = statusReport;
        this.extend = extend;
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ContentMessage)) {
            return false;
        }
        return StringUtil.equals(this.msgId, ((ContentMessage) obj).getMsgId());
    }

    @Override
    public int hashCode() {
        try {
            return this.msgId.hashCode();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
