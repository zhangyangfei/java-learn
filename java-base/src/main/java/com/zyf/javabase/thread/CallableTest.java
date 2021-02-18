package com.zyf.javabase.thread;

//import java.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Callable创建线程
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        // 创建线程，返回结果
        FutureTask<Integer> ft = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "执行call方法...");
                return 1;
            }
        });

        Thread t = new Thread(ft, "t线程");
        t.start();

        // 判断任务是否完成
        boolean done = ft.isDone();
        System.out.println(t.getName() + "是否结束：" + done);

        // 获取线程结果，get()是个阻塞主线程运行的方法
//        Integer result = ft.get();
        // 获取线程结果，get(long timeout, TimeUnit unit)，重载方法，超时则抛出异常
        Integer result = ft.get(2000, TimeUnit.MILLISECONDS);
        System.out.println(t.getName() + "是否结束：" + ft.isDone());
        System.out.println(t.getName() + "执行结果：" + result);
    }
}
