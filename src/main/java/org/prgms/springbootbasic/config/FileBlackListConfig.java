package org.prgms.springbootbasic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file.blacklist")
public record FileBlackListConfig(String fileName){
}
