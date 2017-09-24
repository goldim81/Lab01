package com.lab01;

import java.util.concurrent.Semaphore;

public class Main {
    private static Object endFlag = new Object();
    public volatile static boolean stopAll = false;

    public static void main(String[] args) throws InterruptedException {
        WordStore ws = new WordStore();
        Semaphore s = new Semaphore(1-args.length);
        for (String arg: args) {
            new Thread(new Reader(arg, ws, s)).start();
        }
        s.acquire();
        for (String str:ws.getWordStore()) {
            System.out.println(str);
        }

    }
}
