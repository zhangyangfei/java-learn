package com.zyf.javabase.thread.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 */
public class MyThreadPool2 {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 核心线程数
     */
    private int coreSize;

    /**
     * 最大线程数
     */
    private int maxSize;

    /**
     * 任务队列最大容量
     */
    private int taskCapacity;

    /**
     * 核心线程最大活跃时间（毫秒）
     */
    private long keepAliveTime;

    /**
     * 提交任务最大等待时间（毫秒）
     */
    private long submitTimeOut;

    /**
     * 线程集合
     */
    private CopyOnWriteArraySet<MyThreadWorker2> workers;

    /**
     * 任务队列
     */
    private MyBlokingQueue2<Runnable> taskQueue;

    private MyThreadPool2() {
    }

    public MyThreadPool2(int coreSize, int maxSize, int taskCapacity, long keepAliveTime, long submitTimeOut) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.taskCapacity = taskCapacity;
        this.keepAliveTime = keepAliveTime;
        this.submitTimeOut = submitTimeOut;
        taskQueue = new MyBlokingQueue2<>(this.taskCapacity);
        workers = new CopyOnWriteArraySet<>();
    }

    /**
     * 提交任务
     *
     * @param task
     */
    public boolean submit(Runnable task) {
        synchronized (workers) {
            logger.info("当前存活线程总数：{}", workers.size());
            if (workers.size() < maxSize) {
                MyThreadWorker2 work = new MyThreadWorker2(workers.size() < coreSize);
                work.start();
                workers.add(work);
            }
        }
        // 会阻塞主线程，超时则添加失败
        return taskQueue.syncAdd(task, submitTimeOut, TimeUnit.MILLISECONDS);
    }

    /**
     * 线程工作类（内部类）
     */
    class MyThreadWorker2 extends Thread {

        Logger logger = LoggerFactory.getLogger(getClass());

        /**
         * 是否为核心线程
         */
        private boolean isCore;

        public MyThreadWorker2(boolean isCore) {
            this.isCore = isCore;
        }

        @Override
        public void run() {
            logger.info("启动一个线程,是否为核心线程：{}", isCore);
            Runnable task;
            if (isCore) {
                // 核心线程获取不到任务则阻塞，超时后则死亡
                while ((task = taskQueue.syncGet(keepAliveTime, TimeUnit.MILLISECONDS)) != null) {
                    task.run();
                }
                logger.info("核心线程已超最大活跃时间，死亡：{}", getName());
            } else {
                // 非核心线程直接执行任务，然后死亡
                task = taskQueue.syncGet(keepAliveTime, TimeUnit.MILLISECONDS);
                if(task != null){
                    task.run();
                }
                logger.info("非核心线程死亡：{}", getName());
            }
            this.remove();
        }

        /**
         * 移除线程
         */
        private void remove() {
            synchronized (workers) {
                workers.remove(this);
                logger.info("当前存活线程总数：{}", workers.size());
            }
        }
    }
}