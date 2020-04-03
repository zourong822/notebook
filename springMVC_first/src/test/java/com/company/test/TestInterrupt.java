package com.company.test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试中断interrupt
 */
public class TestInterrupt {
    public static void main(String[] args) {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("begin wait");
//                    System.out.println(Thread.interrupted());
//                    Thread.sleep(2000);
                    synchronized (Thread.currentThread()){
                        Thread.currentThread().wait();
                    }
                    System.out.println("after throwing...");
                } catch (InterruptedException e) {
                    System.out.println("after catching...");
                }
                System.out.println("exit run()...");
            }
        });
        t.start();
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t.interrupt() go");
        t.interrupt();
        System.out.println("t.interrupt() end");
    }
}
