package com.dfire.atomic;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Base64;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author xiaosuda
 * @date 2018/1/4
 */
public class AtomicTest {



    public static AtomicInteger onLinePeople = new AtomicInteger(0);

    public static Integer x = 0;
    public void sessionCreated() {
        x++;
        onLinePeople.addAndGet(1);
    }

    public void sessionDestroyed() {
        x--;
        onLinePeople.decrementAndGet();
    }

    public static void main(String [] args){
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        int threadNum = Runtime.getRuntime().availableProcessors();
        System.out.println(threadNum);
        CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {
            @Override
            public void run() {
                System.out.println("Integer计算人数" + x);
                System.out.println("原子计算人数" + AtomicTest.onLinePeople);
            }
        });
        AtomicTest o = new AtomicTest();
        for (int i = 0; i < threadNum; i++) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        o.sessionCreated();
                    }
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        cachedThreadPool.shutdown();




    }

}
