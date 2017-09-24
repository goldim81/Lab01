package com.lab01;

import java.io.*;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader implements Runnable {
    private static int threadNum = 0;
    private final String threadName;
    private final String fileName;
    private final String splitPattern = "[\\s|,|.|:|;|!|?]+";
    private final Pattern stopPattern = Pattern.compile("[a-zA-Z]+");
    private final WordStore wordStore;
    private final Semaphore semaphore;

    public Reader(String fileName, WordStore wordStore, Semaphore semaphore) {
        threadName = "Thread"+ (++threadNum);
        this.fileName = fileName;
        this.wordStore = wordStore;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        File file = new File(fileName);
        if (file.exists()) {
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                while ((line = br.readLine()) != null) {
                    if (!Main.stopAll) {
                        if (!stopPattern.matcher(line).find()) {
                            String[] str = line.split(splitPattern);

                            for (String s:str) {
                                synchronized (wordStore) {
                                    if (!wordStore.addWord(s)) {
                                        System.out.println(threadName + ": Найдено неуникальное слово - "+s+". Останавлеваем поток.");
                                        Main.stopAll = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            System.out.println(threadName + ": В тексте найдена латиница. Останавлеваем поток.");
                            Main.stopAll = true;
                            break;
                        }
                    } else {
                        System.out.println(threadName + ": Найден признак остановки программы. Останавлеваем поток.");
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(threadName + ": Указанный Файл не существует.");
        }
        semaphore.release();
    }
}
