package com.lab01;

import java.io.*;
import java.util.regex.Pattern;

public class Reader implements Runnable {
    private static volatile boolean stopAll;
    private static int threadNum = 0;
    private final String threadName;
    private final String fileName;
    private final Pattern fileNamePattern = Pattern.compile("[\\w.]+\\.txt");

    private final String splitPattern = "[\\s|,|.|:|;|!|?|«|»|-|\"|\"|%|(|)|—|@|#|$|%|^|&|*|+|]+";
    private final Pattern stopPattern = Pattern.compile("[a-zA-Z]+");
    private final Pattern digitsPattern = Pattern.compile("\\d+");
    private final WordStorage wordStorage;

    public Reader(String fileName, WordStorage wordStorage) {
        threadName = "Thread" + (++threadNum);
        this.fileName = fileName;
        this.wordStorage = wordStorage;
    }

    @Override
    public void run() {
        System.out.println(threadName + " запущен.");
        if (!checkFileName(fileName)) return;

        File file = new File(fileName);
        if (!checkFileExist(file)) return;
        //Основной код обработки файла
        processingFile(file);

        if (stopAll) System.out.println(threadName + ": Найден признак остановки программы. Останавливаем поток.");
        System.out.println(threadName + " остановлен.");
    }

    private boolean checkFileExist(File file) {
        if (!file.exists()) {
            System.out.println(threadName + ": Указанный Файл не существует.");
            System.out.println(threadName + " остановлен.");
            return false;
        }
        return true;
    }

    private boolean checkFileName(String fName) {
        if (!fileNamePattern.matcher(fName).matches()) {
            System.out.println(threadName + ": Шаблон имени файла не соответсвует заданному (*.txt).");
            System.out.println(threadName + " остановлен.");
            return false;
        }
        return true;
    }

    private void processingFile(File file) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null & !stopAll) {
                if (!stopPattern.matcher(line).find()) {
                    saveWordInStorage(line.split(splitPattern), wordStorage);
                } else {
                    System.out.println(threadName + ": В тексте найдена латиница. Останавливаем поток.");
                    stopAll = true;
                    break;}
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveWordInStorage(String[] str, WordStorage store) {
        for (int i = 0; i < str.length & !stopAll; i++) {
            //Пропускаем пустые строки , результат slit'а
            if (str[i].equals("")) continue;
            /*Пропускаем числа, хотя с ними непонятно. Это не слово,
             но по условию они могут содержаться в тексте*/
            if (isDigital(str[i])) continue;
            if (!wordStorage.addWord(str[i])) {
                System.out.println(threadName + ": Найдено не уникальное слово - " + str[i] + ". Останавливаем поток.");
                stopAll = true;
                break;}
        }
    }

    private boolean isDigital(String input) {
        if (digitsPattern.matcher(input).matches()) return true;
        return false;
    }
}
