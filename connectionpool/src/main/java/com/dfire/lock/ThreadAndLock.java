package com.dfire.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author xiaosuda
 * @date 2018/1/11
 */
public class ThreadAndLock {

    private Lock lock = new ReentrantLock();
    private Object o = new Object();

    public static void main(String [] args){

        ThreadAndLock threadAndLock = new ThreadAndLock();
        new Thread(() -> {
                threadAndLock.sharingResource();
        }).start();

        new Thread(() -> {
                threadAndLock.sharingResource();
        }).start();


    }


    private synchronized void sharingResource() {
        System.out.println( "共享资源");

        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


    }
}
