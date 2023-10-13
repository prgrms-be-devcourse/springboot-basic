package com.programmers.springbootbasic.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.file")
public class FileProperties {
    private final String customerBlacklist;
}
