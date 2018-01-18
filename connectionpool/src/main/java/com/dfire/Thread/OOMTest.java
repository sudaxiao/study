package com.dfire.Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 * @author xiaosuda
 * @date 2018/1/18
 */
public class OOMTest {

    private static final Integer taskNum = 5000;
    private static final long sleepTime = 1000;

    public static void main(String [] args) throws InterruptedException {
        problem();
       // solve();
    }


    public static void problem() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i = 0; i < taskNum; i++) {
            executorService.execute(() ->{
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void solve() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1000);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i = 0; i < taskNum; i++) {
            semaphore.acquire();
            executorService.execute(() ->{
                try {
                    Thread.sleep(sleepTime);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
