package com.zyf.javabase.thread.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolTest {

    public static void main(String[] args) {
//        test();
        test2();
    }

    private static void test2() {
        MyThreadPool2 pool = new MyThreadPool2(3, 5, 8, 3000, 2000);
        for (int i = 1; i <= 20; i++) {
            String id = i + "";
            pool.submit(() -> {
                Logger logger = LoggerFactory.getLogger(ThreadPoolTest.class);
//                logger.info("开始执行任务：{}", id);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                logger.info("结束执行任务：{}", id);
            });
        }
        System.out.println("提交任务结束.......");
    }

    private static void test() {
        MyThreadPool pool = new MyThreadPool(2, 5, 8);
        for (int i = 1; i <= 20; i++) {
            String id = i + "";
            pool.submit(() -> {
                Logger logger = LoggerFactory.getLogger(ThreadPoolTest.class);
                logger.info("开始执行任务：{}", id);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("结束执行任务：{}", id);
            });
        }
        System.out.println("提交任务结束.......");
    }
}
