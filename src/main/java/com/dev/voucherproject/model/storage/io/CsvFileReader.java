package com.dev.voucherproject.model.storage.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Component
public class CsvFileReader {
    private static final Logger logger = LoggerFactory.getLogger(CsvFileReader.class);
    private final ResourceLoader resourceLoader;

    public CsvFileReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<String> readAllLines(final String path, final String filename) {
        Resource resource = resourceLoader.getResource(getFileLocation(path, filename));

        try {
            File file = resource.getFile();

            if (file.exists()) {
                List<String> lines = Files.readAllLines(file.toPath());

                if (lines.size() >= 1) {
                    return lines.stream()
                            .takeWhile(line -> !line.isBlank())
                            .toList();
                }
            }
            return Collections.emptyList();
        } catch (IOException e) {
            logger.warn("{} 파일을 읽을 수 없습니다.", filename);
            throw new FileSystemNotFoundException(MessageFormat.format("{} 파일을 읽을 수 없습니다.", filename));
        }
    }

    private String getFileLocation(final String path, final String filename) {
        return "file:%s/%s".formatted(path, filename);
    }
}
