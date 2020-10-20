package com.zyf.javabase.thread.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 队列
 * <p>
 * 先进先出
 * 重入锁、锁的条件变量（等待和唤醒）
 * 获取和存入超时设置
 *
 * @param <T>
 */
public class MyBlokingQueue2<T> {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 队列
     */
    private LinkedList<T> elemList = new LinkedList<>();

    /**
     * 队列最大容量
     */
    private int maxSize;

    private MyBlokingQueue2() {
    }

    public MyBlokingQueue2(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * 线程锁，可重入
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 条件变量-队列已满
     */
    private Condition fullCond = lock.newCondition();

    /**
     * 条件变量-队列已空
     */
    private Condition emptyCond = lock.newCondition();

    /**
     * 同步获取
     *
     * @return
     */
    public T syncGet() {
        return this.syncGet(0, null);
    }

    /**
     * 同步获取
     *
     * @param timeOut 超时时间
     * @param unit    时间单位
     * @return 元素
     */
    public T syncGet(long timeOut, TimeUnit unit) {
        lock.lock();
        long nanos = (timeOut == 0 || unit == null) ? 0 : unit.toNanos(timeOut);
        try {
            while (elemList.isEmpty()) {
                try {
                    logger.info("任务队列已空，等待...");
                    if (nanos == 0) {
                        emptyCond.await();
                    } else {
                        // 超时的等待
                        nanos = emptyCond.awaitNanos(nanos);
                        if (nanos <= 0) {
                            return null;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("从任务队列获取一个任务，唤醒wait线程...");
            T elem = elemList.removeLast();
            fullCond.signalAll();
            return elem;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 同步添加任务
     *
     * @param elem    元素
     * @param timeOut 超时时间
     * @param unit    时间单位
     * @return 是否添加成功
     */
    public boolean syncAdd(T elem, long timeOut, TimeUnit unit) {
        lock.lock();
        long nanos = unit.toNanos(timeOut);
        try {
            while (elemList.size() == maxSize) {
                try {
                    logger.info("任务队列已满，等待...");
                    if (nanos <= 0) {
                        logger.info("添加任务超时，失败...");
                        return false;
                    }
                    nanos = fullCond.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("任务队列新增一个任务，唤醒wait线程...");
            elemList.addFirst(elem);
            emptyCond.signalAll();
            return true;
        } finally {
            lock.unlock();
        }
    }
}