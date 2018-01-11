package com.dfire.Thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 *
 * @author xiaosuda
 * @date 2018/1/8
 */
public class SingleThreadExecutorTest {

    public static void main(String [] args) throws ExecutionException, InterruptedException {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        Random random = new Random();
        Future<Integer> randomSum = singleThreadExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Future<Integer> randomOne = singleThreadExecutor.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return random.nextInt(100);
                    }
                });
                Future<Integer> randomTwo = singleThreadExecutor.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return random.nextInt(100);
                    }
                });
                return randomOne.get() + randomTwo.get();
            }
        });
        System.out.println(randomSum.get());
    }
}
