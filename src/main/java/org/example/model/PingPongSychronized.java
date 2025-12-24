package org.example.model;


public class PingPongSychronized {
    private String string1 = "ping";
    private String string2 = "pong";

    public PingPongSychronized() {
    }

    public synchronized String getString1() {
        return string1 + "-" + Thread.currentThread().getName();
    }

    public synchronized String getString2() {
        return string2 + "-" + Thread.currentThread().getName();

    }

}
