package com.lab01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        WordStorage ws = new WordStorage();

        ExecutorService service = Executors.newCachedThreadPool();
        for(String arg:args){
            service.submit(new Reader(arg, ws));
        }
        service.shutdown();
        while (!service.isTerminated()) {
            Thread.yield();
        }
//        for (String str : ws.getWordStorage()) {
//            System.out.print(str + ", ");
//        }
        System.out.println();
        System.out.println("Всего уникальных слов: " + ws.getWordStorage().size());

        System.out.println("Время выполнения: " + (System.currentTimeMillis()-startTime));
    }
}
