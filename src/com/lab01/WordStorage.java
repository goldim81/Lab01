package com.lab01;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class WordStorage {

//    private CopyOnWriteArraySet<String> wordStorage = new CopyOnWriteArraySet<>();

    private Set<String> wordStorage = new HashSet<>();

    public synchronized Set<String> getWordStorage() {
        return wordStorage;
    }

    public synchronized boolean addWord(String word){
        return wordStorage.add(word);
    }

}
