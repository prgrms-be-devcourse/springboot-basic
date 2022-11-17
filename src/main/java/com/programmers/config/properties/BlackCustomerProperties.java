package com.programmers.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "file.customer.blacklist")
@ConstructorBinding
public class BlackCustomerProperties {
    private final String savePath;

    public BlackCustomerProperties(String savePath) {
        this.savePath = savePath;
    }

    public String getSavePath() {
        return savePath;
    }
}
