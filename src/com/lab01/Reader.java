package com.lab01;

import java.io.*;
import java.util.concurrent.Semaphore;
import java.util.regex.Pattern;

public class Reader implements Runnable {
    private final String fileName;
    private final String splitPattern = "[\\s|,|.|:|;|!|?]";
    private final Pattern stopPattern = Pattern.compile("[a-zA-Z]+");
    private final WordStore wordStore;
    private final Semaphore semaphore;


    public Reader(String fileName, WordStore wordStore, Semaphore semaphore) {
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
                    String[] str = line.split(splitPattern);
                    synchronized (wordStore) {
                        if (!wordStore.addWords(str)) {
                            semaphore.release();
                            return;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Указанный Файл не существует.");
        }
        semaphore.release();
    }
}
