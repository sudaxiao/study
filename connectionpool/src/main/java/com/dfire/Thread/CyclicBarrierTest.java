package com.dfire.Thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author xiaosuda
 * @date 2018/1/8
 */
public class CyclicBarrierTest {
    private CyclicBarrier cyclicBarrier = null;
    private ExecutorService cachedThreadPool;
    private Integer studentsNum;


    public CyclicBarrierTest(Integer studentsNum) {
        this.studentsNum = studentsNum;
        cyclicBarrier = new CyclicBarrier(studentsNum, new Runnable() {
            @Override
            public void run() {
                System.out.println("全部同学到齐，一起去博物馆。");
            }
        });

    }

    public void start() {
        cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < studentsNum; i++) {
            cachedThreadPool.execute(new Student("学生" + i));
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //do some things
                }
            });
        }
        cachedThreadPool.shutdown();
    }

    private class Student implements Runnable {
        private String name;

        public Student(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println(name + "到了");
            try {
                //等待其它学生的到来
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String [] args){

        CyclicBarrierTest cyclicBarrierTest = new CyclicBarrierTest(10);

        cyclicBarrierTest.start();

    }

}
