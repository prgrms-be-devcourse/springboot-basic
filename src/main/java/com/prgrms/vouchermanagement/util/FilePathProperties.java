package com.prgrms.vouchermanagement.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * 애플리케이션에서 사용되는 파일 경로 모음
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "file.path")
public class FilePathProperties {

    private final String vouchersFilePath;
    private final String blackListFilePath;
    private final String baseDirectory;
    private final String sequenceStorePath;

    public FilePathProperties(String vouchersFilePath, String blackListFilePath, String baseDirectory, String sequenceStorePath) {
        this.vouchersFilePath = vouchersFilePath;
        this.blackListFilePath = blackListFilePath;
        this.baseDirectory = baseDirectory;
        this.sequenceStorePath = sequenceStorePath;
    }

    public String getVouchersFilePath() {
        return getFilePath(vouchersFilePath);
    }

    public String getSequenceStorePath() {
        return getFilePath(sequenceStorePath);
    }

    public String getBlackListFilePath() {
        return getFilePath(blackListFilePath);
    }

    private String getFilePath(String fileName) {
        return baseDirectory + "/" + fileName;
    }
}
