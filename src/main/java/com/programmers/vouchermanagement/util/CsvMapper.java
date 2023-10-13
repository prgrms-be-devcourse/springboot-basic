package com.programmers.vouchermanagement.util;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CsvMapper {
    public <T> List<T> readFile(File file, Function<String, T> lineMapper) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            return reader.lines()
                    .skip(1)
                    .map(lineMapper)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file", e);
        }
    }
}
