package com.dfire.Thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author xiaosuda
 * @date 2018/1/18
 */
public class MyThreadPool extends ThreadPoolExecutor {


    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();


    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {


        startTime.set(System.currentTimeMillis());
        super.beforeExecute(t, r);

    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long endTime = System.currentTimeMillis();
        long time = totalTime.addAndGet(endTime - startTime.get());
        System.out.println("耗时:" + time);
        numTasks.incrementAndGet();
        super.afterExecute(r, t);
    }


    @Override
    protected void terminated() {
        System.out.println("线程池关闭");
        super.terminated();
    }

}
