package com.zyf.javabase.thread;

/**
 * 多线程
 */
public class ThreadTest {

    public static void main(String[] args) {

//        interruptSleep();

        interruptNormal();
    }

    /**
     * 打断非阻塞线程
     */
    private static void interruptNormal() {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.interrupted()) {
                    // Thread.interrupted()和Thread.currentThread().isInterrupted()会重置interrupt的值，再次获取就是非打断状态
                    System.out.println("被打断？：" + Thread.interrupted());
                    System.out.println("t1退出循环");
                    break;
                }
            }
        }, "t1线程");
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }

    /**
     * 打断sleep线程，wait、join线程可以相同处理
     */
    private static void interruptSleep() {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
//                e.printStackTrace(); //sleep被打断后此处会抛出异常
            }
            System.out.println("被打断？：" + Thread.interrupted());
            System.out.println("被打断？：" + Thread.currentThread().isInterrupted());
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        }, "t1线程");
        t1.start();
        System.out.println("t1是否被打断：" + t1.isInterrupted());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打断t1线程
        t1.interrupt();
        System.out.println("t1是否被打断：" + t1.isInterrupted());
        System.out.println("t1是否被打断：" + t1.isInterrupted());
        System.out.println("主线程执行完毕");
    }
}