package com.cj.chat.event;

/**
 * @author cj
 * @description: 事件对象池
 * @date :2019/6/7 15:42
 */
public class CEventObjPool extends ObjectPool<CEvent> {

    public CEventObjPool(int capacity) {
        super(capacity);
    }

    @Override
    protected CEvent[] createObjPool(int capacity) {
        return new CEvent[capacity];
    }

    @Override
    protected CEvent createNewObj() {
        return new CEvent();
    }
}
