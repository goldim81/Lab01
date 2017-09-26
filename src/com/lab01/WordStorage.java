package com.lab01;

import java.util.*;

public class WordStorage {
    //Среднее время выполнения 86,9
//    private CopyOnWriteArraySet<String> wordStorage = new CopyOnWriteArraySet<>();
    //Среднее время выполнения 53,3
    private Set<String> wordStorage = new HashSet<>();

    public synchronized Set<String> getWordStorage() {
        return wordStorage;
    }

    public synchronized boolean addWord(String word){
        return wordStorage.add(word);
    }

}
