package com.dojinyou.devcourse.voucherapplication.common.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileUtils {
    private static final String SEPARATOR = ",";

    public static List<String[]> read(Path path) {
        try {
            List<String> readData = Files.readAllLines(path);
            return readData.stream()
                           .map((line) -> line.split(SEPARATOR))
                           .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("");
        }
    }

    public static String[] readLine(Path path, int lineNumber) {
        try {
            List<String> readData = Files.readAllLines(path);
            if (readData.size() == 0) {
                throw new IOException("데이터가 없습니다.");
            }
            if (Math.abs(lineNumber) >= readData.size()) {
                throw new IOException("lineNumber가 너무 큽니다.");
            }

            if (lineNumber < 0) {
                lineNumber += readData.size();
            }

            String line = readData.get(lineNumber);
            return line.split(SEPARATOR);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static void write(Path path, String[] data) {
        try {
            String flatData = String.join(SEPARATOR, data);
            flatData = flatData + System.lineSeparator();
            Files.write(path, flatData.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalArgumentException("");
        }
    }
}
