package org.example.model;


public class PingPong {
    private String string1;
    private String string2;

    public PingPong() {
        this.string1 = sayPing();
        this.string2 = sayPong();
    }

    private String sayPing() {
        string1 = "ping";
        return string1;
    }

    private String sayPong() {
        string2 = "pong";
        return string2;
    }

    public String getString1() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return string1 + "-" + Thread.currentThread().getName();
    }

    public String getString2() {

        return string2 + "-" + Thread.currentThread().getName();

    }

}
