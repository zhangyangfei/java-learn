package com.zyf.javabase.thread.valotileDemo;

/**
 * volatile
 * <p>
 * 修饰的变量具备多线程可见性（需要从主内存读取变量的值）
 * 可作为触发器（对变量的操作具备原子性，例如给boolean赋值）
 */
public class VolatileTest {

    // 触发器：被volatile修饰
    private static volatile boolean trigger = false;

    public static void main(String[] args) {
        // 修改触发器的线程
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":" + i);
                // 3的倍数则让触发器为true
                trigger = i % 3 == 0;
            }
        }, "触发条件").start();

        // 使用触发器的线程
        new Thread(() -> {
            /*
            while (!trigger) {
            }
            System.out.println(Thread.currentThread().getName() + ":" + "我被触发了。。。");
            */
            while (true) {
                // 判断触发器的值是否为true（从主内存读取值，而不是从线程工作内存）
                if (trigger) {
                    System.out.println(Thread.currentThread().getName() + ":" + "我被触发了。。。");
                    trigger = false;
                }
            }
        }, "触发后").start();
    }
}
