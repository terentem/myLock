package org.example;

import org.example.model.PingPong;
import org.openjdk.jol.info.ClassLayout;

//При сатрті потоку "a1" він захоплює монітор на 30 сек (mark word стає thin lock).
// Через 10 секунд стартує поток b1, намагається отримати доступ до монітора pingpong,
// JVM бачить конкуренцію і створює object monitor для pingpong (fat lock).
// main чекає 5 секунд на виконання b1 ставить його в EntryList, запускає наступний потів b[i].
// Потік a1 назавжди забирає монітор, але сповіщає JVM через Thread.yeald, по можна "мене приривати" на інші потоки.
// По факту працюватиме тільки 1 потів a1.

public class MyStarvation {
    public static void main(String[] args) throws InterruptedException {
        PingPong pingPong = new PingPong();
        Runnable runnablePing = new Runnable() {
            @Override
            public void run() {

                while (true) {
                    synchronized (pingPong) {
                        System.out.println("***********************************");
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(ClassLayout.parseInstance(pingPong).toPrintable());
                        System.out.println(pingPong.getString1());

                    }
                }
            }
        };
        Runnable runnablePong = new Runnable() {
            @Override
            public void run() {
                synchronized (pingPong) {
                    System.out.println("***********************************");
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(pingPong.getString2());
                }
            }
        };

        Thread a = new Thread(runnablePing, "-a1");
        a.start();
        Thread.sleep(10000);
        for (
                int i = 0;
                i < 4; i++) {
            Thread b = new Thread(runnablePong, "-b" + i);
            b.start();
            System.out.println("is a1 alive = " + a.isAlive());
            b.join(5000);
        }
        System.out.println(ClassLayout.parseInstance(pingPong).toPrintable());
    }
}
