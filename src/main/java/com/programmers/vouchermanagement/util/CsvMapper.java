package com.programmers.vouchermanagement.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
public class CsvMapper {
    private String readHeader(File file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            return reader.readLine();
        } catch (IOException e) {
            String message = "Failed to read CSV header";
            log.error(message, e);
            throw new RuntimeException(message, e);
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
            String message = "Failed to read CSV line";
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }
}
