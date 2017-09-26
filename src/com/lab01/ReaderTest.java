package com.lab01;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {
    static private Reader reader;
    static private WordStorage storage;

    @BeforeAll
    static void createReader(){
        storage = new WordStorage();
        reader = new Reader("Data01.txt", storage);
    }

    @Test
    void checkFileExist() {
        File file = new File("Data01.txt");
        assertTrue(reader.checkFileExist(file));
    }

    @Test
    void checkFileName() {
        assertTrue(reader.checkFileName("Data01.txt"));
        assertFalse(reader.checkFileName("data.dat"));
    }

    @Test
    void processingFile() {
        Set set = new HashSet<>(Arrays.asList(new String[] {"Необходимо", "разработать", "программу"}));
        File file = new File("Test.txt");
        reader.processingFile(file);
        assertEquals(set, storage.getWordStorage());
    }

    @Test
    void saveWordInStorage() {
        WordStorage ws = new WordStorage();
        String[] strArr = new String[] {"Тест", "Тест2", "Тест3"};
        reader.saveWordInStorage(strArr, ws);
        Set set = new HashSet<>(Arrays.asList(strArr));
        assertEquals(set, ws.getWordStorage());
    }

    @Test
    void isDigital() {
        assertTrue(reader.isDigital("123"));
        assertFalse(reader.isDigital("f123"));
        assertFalse(reader.isDigital("asdf"));
    }

}