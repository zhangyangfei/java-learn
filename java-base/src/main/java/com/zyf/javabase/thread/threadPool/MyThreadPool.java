package com.zyf.javabase.thread.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 自定义线程池
 */
public class MyThreadPool {

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
     * 线程集合
     */
    private CopyOnWriteArraySet<MyThreadWorker> workers;

    /**
     * 任务队列
     */
    private MyBlokingQueue<Runnable> taskQueue;

    private MyThreadPool() {
    }

    public MyThreadPool(int coreSize, int maxSize, int taskCapacity) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        taskQueue = new MyBlokingQueue<>(taskCapacity);
        workers = new CopyOnWriteArraySet<>();
    }

    /**
     * 提交任务
     *
     * @param task
     */
    public void submit(Runnable task) {
        synchronized (workers) {
            if (workers.size() < maxSize) {
                MyThreadWorker work = new MyThreadWorker(workers.size() < coreSize);
                work.start();
                workers.add(work);
            }
        }
        // 会阻塞主线程，不友好
        taskQueue.syncAdd(task);

    }

    /**
     * 线程工作类（内部类）
     */
    class MyThreadWorker extends Thread {

        Logger logger = LoggerFactory.getLogger(getClass());

        // 是否为核心线程
        private boolean isCore;

        public MyThreadWorker(boolean isCore) {
            this.isCore = isCore;
        }

        @Override
        public void run() {
            Runnable task;
            if (isCore) {
                logger.info("启动一个核心线程{}", getName());
                while ((task = taskQueue.syncGet()) != null) {
                    task.run();
                }
            } else {
                logger.info("启动一个非核心线程{}", getName());
                taskQueue.syncGet().run();
                synchronized (workers) {
                    workers.remove(this);
                }
            }
        }
    }
}