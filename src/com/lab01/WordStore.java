package com.lab01;

import java.util.*;

public class WordStore {
    private Set<String> wordStore = new HashSet<>();

    public Set<String> getWordStore() {
        return wordStore;
    }

    public boolean addWord(String word){
        return wordStore.add(word);
    }

    public boolean addWords(String[] words){
        return wordStore.addAll(Arrays.asList(words));
    }
}
