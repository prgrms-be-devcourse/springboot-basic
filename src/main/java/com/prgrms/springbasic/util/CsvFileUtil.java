package com.prgrms.springbasic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class CsvFileUtil {
    private static final Logger log = LoggerFactory.getLogger(CsvFileUtil.class);

    public static void addObjectToFile(String filePath, String fieldToCsvString) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(fieldToCsvString);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> Map<UUID, T> readItemsFromFile(String filePath, Function<String[], T> mapper) {
        Map<UUID, T> items = new ConcurrentHashMap<>();
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                UUID itemId = UUID.fromString(parts[0]);
                T item = mapper.apply(parts);
                items.put(itemId, item);
            });
        } catch (IOException e) {
            log.warn("The file does not exist. fileName : {}", filePath);
        }
        return items;
    }

    public static void writeItemsToFile(String filePath, List<String> fieldToCsvStrings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String s : fieldToCsvStrings) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            log.warn("Error writing items to file: {}", e.getMessage());
        }
    }

    public static void deleteAllFileContent(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write("");
        } catch (IOException e) {
            log.warn("Error writing items to file: {}", e.getMessage());
        }
    }
}
