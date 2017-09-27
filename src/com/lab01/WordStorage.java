package com.lab01;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WordStorage {

    private Set <String> wordStorage = ConcurrentHashMap.newKeySet();

//    private Set<String> wordStorage = new HashSet<>();

    public Set<String> getWordStorage() {
        return wordStorage;
    }

    public boolean addWord(String word){
        return wordStorage.add(word);
    }

}
