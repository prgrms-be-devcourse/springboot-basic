package com.programmers.voucher.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;

@ConfigurationProperties(prefix = "file.customer.blacklist")
@ConstructorBinding
public class CustomerProperties {
    private final String savePath;

    public CustomerProperties(String savePath) {
        this.savePath = savePath;
    }

    public String getSavePath() {
        return savePath;
    }
}
