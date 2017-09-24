package com.lab01;

import java.util.*;

public class WordStore {
    private Set<String> wordStore = new HashSet<>();

    public Set<String> getWordStore() {
        return wordStore;
    }

    public synchronized boolean addWord(String word){
        return wordStore.add(word);
    }
}
