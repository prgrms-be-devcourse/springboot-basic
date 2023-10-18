package org.programmers.springboot.basic.util.manager;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CSVFileManager {

    public BufferedWriter getBufferedWriter(File FILE) throws IOException {
        return new BufferedWriter(getFIleWriter(FILE));
    }

    public FileWriter getFIleWriter(File FILE) throws IOException {
        return new FileWriter(FILE, true);
    }

    public BufferedReader getBufferedReader(File FILE) throws IOException {
        return new BufferedReader(getFileReader(FILE));
    }

    public FileReader getFileReader(File FILE) throws IOException {
        return new FileReader(FILE);
    }
}
