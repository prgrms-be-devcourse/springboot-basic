package org.programmers.kdt.weekly.voucher.repository;


import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileBuffer {
    private static BufferedWriter bufferedWriter;
    private static BufferedReader bufferedReader;

    public static BufferedReader getBufferedReader(File file) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }

    public static BufferedWriter getBufferWriter(File file) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedWriter;
    }

}
