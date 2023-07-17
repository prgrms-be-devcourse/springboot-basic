package org.programmers.VoucherManagement.global.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.path")
public class FileInfo {
    private final String filePath;

    public FileInfo(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
