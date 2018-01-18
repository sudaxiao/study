package com.dfire.Thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author xiaosuda
 * @date 2018/1/18
 */
public class ConcurrencyError {

    private Integer x = 0;
    private AtomicInteger sum = new AtomicInteger();
    private Integer NUM_TASKS = 10;
    private Integer ADD_TIMES = 1000;
    private CyclicBarrier cyclicBarrier;
    public static void main(String [] args){
        new ConcurrencyError().test();
    }

    public void test() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        cyclicBarrier = new CyclicBarrier(NUM_TASKS, new Runnable() {
            @Override
            public void run() {
                System.out.println("最终结果为:x=" + x + " sum=" + sum.get());
            }
        });

        for (int i = 0; i < NUM_TASKS; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < ADD_TIMES; j++) {
                    x = x + 1;
                    sum.incrementAndGet();
                }
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }
}
