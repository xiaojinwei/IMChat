package com.cj.im;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author cj
 * @description: 线程池工厂，负责重连和心跳线程调度
 * @date :2019/6/1 15:28
 */
public class ExecutorServiceFactory {

    private ExecutorService bossPool;//管理线程组，负责重连
    private ExecutorService workPool;//工作线程组，负责心跳

    /**
     * 初始化boss线程池
     */
    public synchronized void initBossLoopGroup() {
        initBossLoopGroup(1);
    }

    /**
     * 初始化boss线程池
     * 重载方法
     * @param size 线程池大小
     */
    public synchronized void initBossLoopGroup(int size) {
        destroyBossLoopGroup();
        bossPool = Executors.newFixedThreadPool(size);
    }

    /**
     * 释放boss线程池
     */
    public synchronized void destroyBossLoopGroup() {
        if (bossPool != null) {
            try {
                bossPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            }finally {
                bossPool = null;
            }
        }
    }

    /**
     * 初始化work线程池
     */
    public synchronized void initWorkLoopGroup() {
        initWorkLoopGroup(1);
    }

    /**
     * 初始化work线程池
     * 重载方法
     * @param size 线程池大小
     */
    public synchronized void initWorkLoopGroup(int size) {
        destroyWorkLoopGroup();
        workPool = Executors.newFixedThreadPool(size);
    }

    /**
     * 释放work线程池
     */
    public synchronized void destroyWorkLoopGroup() {
        if (workPool != null) {
            try{
                workPool.shutdownNow();
            }catch (Throwable t){
                t.printStackTrace();
            }finally {
                workPool = null;
            }
        }
    }

    /**
     * 执行boss任务
     * @param r
     */
    public void execBossTask(Runnable r) {
        if (bossPool == null) {
            initBossLoopGroup();
        }
        bossPool.execute(r);
    }

    /**
     * 执行work任务
     * @param r
     */
    public void execWorkTask(Runnable r) {
        if (workPool == null) {
            initWorkLoopGroup();
        }
        workPool.execute(r);
    }

    /**
     * 释放所有线程池
     */
    public synchronized void destroy() {
        destroyBossLoopGroup();
        destroyWorkLoopGroup();
    }
}
