package com.zyf.javabase.thread.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者模式
 * <p>
 * wait()、notifyAll()
 */
public class ProducerConsumer {

    Logger logger = LoggerFactory.getLogger(getClass());

    // 共享数据
    private AtomicInteger counter = new AtomicInteger(0);

    // 每次生产的最大数量
    private int maxSize = 3;

    /**
     * 生产者
     */
    class Producer {

        //生产线程
        public void producer() {
            new Thread(() -> {
                execute();
            }, "生产者线程").start();
        }

        private void execute() {
            synchronized (counter) {
                while (true) {
                    if (counter.get() >= maxSize) {
                        try {
                            logger.info("{}...产品充足，等待消费", counter);
                            counter.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 自增
                    counter.getAndIncrement();
                    logger.info("{}...开始生产", counter);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("{}...结束生产，通知消费者", counter);
                    counter.notify();
                }
            }
        }
    }

    /**
     * 消费者
     */
    class Consumer {

        // 消费线程
        public void consumer() {
            new Thread(() -> {
                execute();
            }, "消费者线程").start();
        }

        private void execute() {
            synchronized (counter) {
                while (true) {
                    if (counter.get() == 0) {
                        try {
                            logger.info("{}...无产品，等待生产", counter);
                            counter.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    logger.info("{}...开始消费", counter);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("{}...结束消费，通知生产者", counter);
                    // 自减
                    counter.getAndDecrement();
                    counter.notify();
                }
            }
        }
    }
}