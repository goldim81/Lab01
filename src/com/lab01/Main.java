package com.lab01;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        WordStorage ws = new WordStorage();

        Thread[] threads = new Thread[args.length];
        for (int i = 0; i < args.length; i++) {
            threads[i] = new Thread(new Reader(args[i], ws));
            threads[i].start();
        }
//        Ждем завершения всех потоков, всего вариантов запуска и ожидания потоков 3
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Вариант с пулом работает в 2-3 раза дольше предыдущего
//        ExecutorService service = Executors.newFixedThreadPool(6);
//        for(String arg:args){
//            service.submit(new Reader(arg, ws));
//        }
//        service.shutdown();
//        while (!service.isTerminated()) {
////            Thread.sleep(100);
//        }
        for (String str : ws.getWordStorage()) {
            System.out.print(str + ", ");
        }
        System.out.println();
        System.out.println("Всего уникальных слов: " + ws.getWordStorage().size());

        System.out.println("Время выполнения: " + (System.currentTimeMillis()-startTime));
    }
}
