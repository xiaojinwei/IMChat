package com.cj.chat.event;

/**
 * @author cj
 * @description: 对象池中的对象要求实现PoolableObject接口
 * @date :2019/6/7 15:17
 */
public interface PoolableObject {

    /**
     * 恢复到默认状态
     */
    void reset();
}
