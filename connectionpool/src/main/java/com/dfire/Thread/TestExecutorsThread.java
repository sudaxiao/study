package com.dfire.Thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 *
 * @author xiaosuda
 * @date 2018/1/3
 */
public class TestExecutorsThread {
    private static Integer MAX_TASK = 100;
    private static Semaphore semaphore = new Semaphore(MAX_TASK);
    public static void main(String [] args) throws InterruptedException {
       // cacheThreadPool();
        testSubmit();
    }
    private static void cacheThreadPool() throws InterruptedException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            semaphore.acquire();
            cachedThreadPool.execute(() ->{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
        }
        cachedThreadPool.shutdown();
    }

    private static void testSubmit() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Integer> randomNum = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        });
        try {
            System.out.println(randomNum.get());
        } catch (InterruptedException e) {
            //中断异常
            e.printStackTrace();
        } catch (ExecutionException e) {
            // 任务异常
            e.printStackTrace();
        } finally {
            //关闭线程池
            executorService.shutdown();
        }


    }
}
