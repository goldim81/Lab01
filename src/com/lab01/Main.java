package com.lab01;

public class Main {
    public volatile static int endThreadFlag =0;
    public volatile static boolean stopAll = false;

    public static void main(String[] args) throws InterruptedException {
        WordStore ws = new WordStore();
        endThreadFlag -= args.length;
        for (String arg: args) {
            new Thread(new Reader(arg, ws)).start();
        }
        //Ждем завершения всех потоков
        while (endThreadFlag <0) {
            Thread.sleep(500);
        }
        for (String str:ws.getWordStore()) {
            System.out.println(str);
        }
        System.out.println("Всего уникальных слов: " + ws.getWordStore().size());

    }
}
