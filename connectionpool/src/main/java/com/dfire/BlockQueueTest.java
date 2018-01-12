package com.dfire;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author xiaosuda
 * @date 2018/1/12
 */
public class BlockQueueTest {

    private ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10);

    public static void main(String [] args){

        BlockQueueTest blockQueueTest = new BlockQueueTest();


        try {
            Integer poll = blockQueueTest.blockingQueue.poll(5, TimeUnit.SECONDS);
            System.out.println(poll);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
