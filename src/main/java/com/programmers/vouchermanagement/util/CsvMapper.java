package com.programmers.vouchermanagement.util;

import java.io.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvMapper {
    private String readHeader(File file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV header", e);
        }
    }

    public <T> List<T> readFile(File file, BiFunction<String, String, T> lineMapper) {
        String header = readHeader(file);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            return reader.lines()
                    .skip(1)
                    .map(line -> lineMapper.apply(header, line))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file", e);
        }
    }
}
