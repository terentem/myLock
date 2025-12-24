package org.example;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        if (args[0].equals("deadlock")) {
            MyDeadlock.main(args);
        }

    }
}