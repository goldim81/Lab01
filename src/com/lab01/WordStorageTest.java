package com.lab01;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordStorageTest {
    private WordStorage wordStorage;

    @BeforeEach
    void createStorage() {
        wordStorage = new WordStorage();
    }

    @Test
    void getWordStorage() {

        wordStorage.addWord("Тест");
        assertEquals(1, wordStorage.getWordStorage().size());
    }

    @Test
    void addWord() {
//        WordStorage wordStorage = new WordStorage();
        wordStorage.addWord("Тест");
        assertEquals("Тест", wordStorage.getWordStorage().toArray(new String[1])[0]);
    }

}