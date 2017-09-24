package com.lab01;

import java.io.*;
import java.util.Random;
import java.util.regex.Pattern;

public class Reader implements Runnable {
    private static int threadNum = 0;
    private final String threadName;
    private final String fileName;
    private final Pattern fileNamePattern = Pattern.compile("[\\w.]+\\.txt");

    private final String splitPattern = "[\\s|,|.|:|;|!|?|«|»|-|\"|\"|%|0-9|(|)|—|@|#|$|%|^|&|*|+|]+";
    private final Pattern stopPattern = Pattern.compile("[a-zA-Z]+");
    private final WordStore wordStore;

    public Reader(String fileName, WordStore wordStore) {
        threadName = "Thread" + (++threadNum);
        this.fileName = fileName;
        this.wordStore = wordStore;
    }

    @Override
    public void run() {
        System.out.println(threadName + " запущен.");
        if (!fileNamePattern.matcher(fileName).matches()) {
            System.out.println(threadName + ": Шаблон имени файла не соответсвует заданному (*.txt).");
            System.out.println(threadName + " остановлен.");
            return;
        }
        File file = new File(fileName);
        if (file.exists()) {
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                while ((line = br.readLine()) != null & !Main.stopAll) {
                    if (!stopPattern.matcher(line).find()) {
                        String[] str = line.split(splitPattern);
                        //Засунул допусловие в условие цикла для избавления от лишнего IF
                        for (int i = 0; i <str.length & !Main.stopAll ; i++) {
                            if (str[i].equals("")) continue;
                            synchronized (wordStore) {
                                if (!wordStore.addWord(str[i])) {
                                    System.out.println(threadName + ": Найдено неуникальное слово - " + str[i] + ". Останавливаем поток.");

                                    Main.stopAll = true;
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println(threadName + ": В тексте найдена латиница. Останавливаем поток.");
                        Main.stopAll = true;
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
        if (Main.stopAll) System.out.println(threadName + ": Найден признак остановки программы. Останавливаем поток.");
        System.out.println(threadName + " остановлен.");
    }
}
