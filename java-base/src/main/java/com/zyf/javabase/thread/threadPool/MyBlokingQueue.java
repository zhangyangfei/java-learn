package com.zyf.javabase.thread.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * 队列
 * <p>
 * synchronized、wait()、notifyALL()
 *
 * @param <T>
 */
public class MyBlokingQueue<T> {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 任务队列
     */
    private LinkedList<T> taskList = new LinkedList<>();

    /**
     * 任务队列最大容量
     */
    private int maxSize;

    private MyBlokingQueue() {
    }

    public MyBlokingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * 同步获取
     *
     * @return
     */
    public T syncGet() {
        synchronized (taskList) {
            while (taskList.isEmpty()) {
                try {
                    logger.info("任务队列已空，等待...");
                    taskList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("从任务队列获取一个任务，唤醒其他wait线程...");
            T task = taskList.removeLast();
            taskList.notifyAll();
            return task;
        }
    }

    /**
     * 同步添加任务
     *
     * @param task
     */
    public void syncAdd(T task) {
        synchronized (taskList) {
            while (taskList.size() == maxSize) {
                try {
                    logger.info("任务队列已满，等待...");
                    taskList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("任务队列新增一个任务，唤醒其他wait线程...");
            taskList.addFirst(task);
            taskList.notifyAll();
        }
    }
}
