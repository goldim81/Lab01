package com.lab01;

import java.util.*;

public class WordStorage {
    //Среднее время выполнения 86,9
//    private CopyOnWriteArraySet<String> wordStore = new CopyOnWriteArraySet<>();
    //Среднее время выполнения 53,3
    private Set<String> wordStore = new HashSet<>();

    public Set<String> getWordStorage() {
        return wordStore;
    }

    public synchronized boolean addWord(String word){
        return wordStore.add(word);
    }
}
