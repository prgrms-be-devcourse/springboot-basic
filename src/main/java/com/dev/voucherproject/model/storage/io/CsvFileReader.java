package com.dev.voucherproject.model.storage.io;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CsvFileReader {
    private final ResourceLoader resourceLoader;

    public CsvFileReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    public Optional<String> findLineByWord(final String word, final String path, final String filename) {
        Resource resource = getResource(path, filename);

        try {
            File file = resource.getFile();

            try (BufferedReader reader =  new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");

                    if (data[1].equals(word)) {
                        return Optional.of(line);
                    }
                }
            }
        } catch (IOException e) {
            throw new FileSystemNotFoundException(MessageFormat.format("{0} 파일을 읽을 수 없습니다.", filename));
        }

        return Optional.empty();
    }


    public List<String> readAllLines(final String path, final String filename) {
        Resource resource = getResource(path, filename);

        try {
            File file = resource.getFile();
            List<String> lines = Files.readAllLines(file.toPath());

            return extractNotBlankLines(lines);
        } catch (IOException e) {
            throw new FileSystemNotFoundException(MessageFormat.format("{0} 파일을 읽을 수 없습니다.", filename));
        }
    }

    private List<String> extractNotBlankLines(List<String> lines) {
        if (!lines.isEmpty()) {
            return lines.stream()
                    .filter(line -> !line.isBlank())
                    .toList();
        }

        return Collections.emptyList();
    }

    private Resource getResource(String path, String filename) {
        return resourceLoader.getResource(getFileLocation(path, filename));
    }

    private String getFileLocation(final String path, final String filename) {
        return "file:%s/%s".formatted(path, filename);
    }
}
