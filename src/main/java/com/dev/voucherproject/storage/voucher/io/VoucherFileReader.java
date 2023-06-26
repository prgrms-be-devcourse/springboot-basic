package com.dev.voucherproject.storage.voucher.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

@Component
public class VoucherFileReader {
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileReader.class);
    @Value("${voucher.path}")
    private String path;
    @Value("${voucher.filename}")
    private String filename;
    private final ResourceLoader resourceLoader;

    public VoucherFileReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<String> readAllLines() {
        Resource resource = resourceLoader.getResource(getFileLocation());

        try {
            File file = resource.getFile();

            if (!file.exists()) {
                return Collections.emptyList();
            }

            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            logger.warn("{} 파일을 찾을 수 없습니다.", path);
            throw new FileSystemNotFoundException("파일을 찾을 수 없습니다.");
        }
    }
    private String getFileLocation() {
        return "file:%s/%s".formatted(path, filename);
    }
}
