package com.lab01;

public class Main {

    public volatile static boolean stopAll = false;

    public static void main(String[] args) {
        WordStore ws = new WordStore();
        Thread[] threads = new Thread[args.length];
        for (int i = 0; i < args.length; i++) {
            threads[i] = new Thread(new Reader(args[i], ws));
            threads[i].start();
        }
        //Ждем завершения всех потоков, всего вариантов запуска и ожидания потоков 3
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (stopAll) {
            System.out.println("Потоки завершены из-за ошибки в тексте.");
        } else {
            for (String str : ws.getWordStore()) {
                System.out.println(str);
            }
            System.out.println("Всего уникальных слов: " + ws.getWordStore().size());
        }

    }
}
