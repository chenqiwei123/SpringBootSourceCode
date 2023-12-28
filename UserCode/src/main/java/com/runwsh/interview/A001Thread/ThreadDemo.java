package com.runwsh.interview.A001Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ThreadDemo {
    public static void main(String[] args) {
        // 使用Thread类创建线程
        CreateThread();
        // 实现Runnable接口创建线程
        ImplementRunnable();
        // 实现Callable接口创建线程
        ImplementCallable();
        // 使用ExecutorService线程池创建线程
        ImplementExecutorService();
    }

    private static void ImplementExecutorService() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {
            public void run() {
                System.out.println("使用ExecutorService线程池创建线程==线程执行完毕");
            }
        });
    }

    private static void ImplementCallable() {
        try {
            // 创建Callable对象
            CallableDemo callableDemo = new CallableDemo();
            // 创建FutureTask对象
            FutureTask<String> futureTask = new FutureTask<String>(callableDemo);
            // 创建线程
            Thread thread = new Thread(futureTask);
            // 启动线程
            thread.start();
            System.out.println("实现Callable接口创建线程==线程启动");
            // 等待线程执行完毕
            System.out.println(futureTask.get());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static class CallableDemo implements Callable<String> {
        public String call() throws Exception {
            return "实现Callable接口创建线程==线程执行完毕";
        }
    }

    private static void ImplementRunnable() {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("实现Runnable接口创建线程==线程启动");
            }
        }).start();
    }

    /**
     * 继承Thread类创建线程
     */
    private static void CreateThread() {
        Thread thread = new Thread() {
            public void run() {
                System.out.println("继承Thread类创建线程==线程启动");
            }
        };
        thread.start();
    }


}
