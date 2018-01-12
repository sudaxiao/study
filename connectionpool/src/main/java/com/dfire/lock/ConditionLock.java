package com.dfire.lock;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author xiaosuda
 * @date 2018/1/11
 */
public class ConditionLock {

    private Lock lock = new ReentrantLock();

    private Condition isFull = lock.newCondition();

    private Condition isEmpty = lock.newCondition();

    private AtomicInteger num = new AtomicInteger(0);


    @Data
    private class Apple {
        private String name;

        public Apple(String name) {
            this.name = name;
        }
    }

    @Data
    private class Basket {
        private List<Apple> apples;
        private Integer size;
        public Basket(int size) {
            this.apples = new ArrayList<>(size);
            this.size = size;
        }

        public void put(Apple apple) {
            lock.lock();
            try {
                if (apples.size() == size) {
                    System.out.println("苹果满了，等待使用");
                    isFull.await();
                }
                apples.add(apple);
                System.out.println("放入苹果:" + apple.getName() + "篮子大小为:" + apples.size());
                isEmpty.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public Apple take() {
            lock.lock();
            Apple apple = null;
            try {
                if (apples.size() == 0) {
                    System.out.println("苹果没了，等待生产");
                    isEmpty.await();
                }
                apple = apples.remove(0);
                System.out.println("取出苹果:" + apple.getName() + "篮子大小为:" + apples.size());
                isFull.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return apple;
        }
    }
    @Data
    private class Product implements Runnable{

        private Basket basket;
        private boolean stop;

        public Product(Basket basket) {
            this.basket = basket;
            this.stop = false;
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    Thread.sleep(1000);
                    basket.put(new Apple("苹果" + num.addAndGet(1)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Data
    private class Consumer implements Runnable{

        private Basket basket;
        private boolean stop;

        public Consumer(Basket basket) {
            this.basket = basket;
            this.stop = false;
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    Thread.sleep(10);
                    basket.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String [] args){
        ConditionLock conditionLock = new ConditionLock();
        Basket basket = conditionLock.new Basket(10);
        Consumer consumerOne = conditionLock.new Consumer(basket);
        Consumer consumerTwo = conditionLock.new Consumer(basket);
        Product product = conditionLock.new Product(basket);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,4,60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1), new ThreadPoolExecutor.AbortPolicy());

        threadPoolExecutor.allowCoreThreadTimeOut(true);

        threadPoolExecutor.execute(consumerOne);
        threadPoolExecutor.execute(consumerTwo);
        threadPoolExecutor.execute(product);
        threadPoolExecutor.shutdown();
    }
}
