package com.mountain.voucherApp.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "file-repository")
@ConstructorBinding
public class FileRepositoryProperties {

    private final String dir;
    private final String fileName;

    public FileRepositoryProperties(String dir, String fileName) {
        this.dir = dir;
        this.fileName = fileName;
    }

    public String getDir() {
        return dir;
    }
    public String getFileName() {
        return fileName;
    }
}
