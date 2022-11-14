package com.programmers.kwonjoosung.springbootbasicjoosung.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "file.customer-black-list")
public class FileCustomerBlackListProperties {

    private final String name;

    private final String path;

    public FileCustomerBlackListProperties(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getFileName() {
        return name;
    }

    public String getFilePath() {
        return path;
    }

}
