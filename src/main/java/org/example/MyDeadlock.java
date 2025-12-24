package org.example;

import org.example.model.PingPong;

public class MyDeadlock {
    public static void main(String[] args) throws InterruptedException {
        PingPong pingPong1 = new PingPong();
        PingPong pingPong2 = new PingPong();
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                synchronized (pingPong1) {
                    System.out.println("блокую pingPong1 на thin lock. Доступ до pingPong1 ще є");
                    System.out.println(pingPong1.getString1() + "first ping");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (pingPong2) {
                        System.out.println("поток t1 викликає pingPong2 і створює для нього fat lock. Виконання наступного коду неможливе. Потік а переходить в EntryList.");
                        System.out.println(pingPong2.getString2());
                    }
                }
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                synchronized (pingPong2) { // 1. Захватили монитор 2
                    System.out.println("створення thin lock для pingPong2 ");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (pingPong1) {
                        System.out.println("створення fat lock для pingPong1. Тепер pingPong1 заблоковано для виконання наступного коду." +
                                "Поток t2 переходить в EntryList поки не звільниться pingPong1 ");
                        System.out.println(pingPong1.getString2());
                    }
                }
            }
        };

        Thread t1 = new Thread(runnable1, "-t1");
        t1.start();
        System.out.println("t1 started.");
        //Thread.sleep(1000);
        Thread t2 = new Thread(runnable2, "-t2");
        t2.start();
        System.out.println("t2 started.");
    }
}