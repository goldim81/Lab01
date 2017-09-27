package com.lab01;

import java.io.*;

public class CreateFile {
    public static void main(String[] args) {
        String word = "водосток";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data7.txt"))){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 10; j++) {
                    sb.append(word).append(i).append(j).append(" ");
                }
                writer.write(sb.toString()+"\n");
                sb.delete(0,sb.length());
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
