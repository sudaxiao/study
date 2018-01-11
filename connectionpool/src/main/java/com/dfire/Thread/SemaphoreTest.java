package com.dfire.Thread;

import java.util.concurrent.Semaphore;

/**
 *
 * @author xiaosuda
 * @date 2018/1/3
 */
public class SemaphoreTest {

    public static void main(String [] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);

        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        semaphore.release();
                        System.out.println("生成信号");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        for (int i = 0; i < 10; i++) {
            semaphore.acquire();
            System.out.println(i);
        }



    }
}
