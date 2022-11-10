package com.programmers.voucher.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;

@ConfigurationProperties(prefix = "file.voucher")
@ConstructorBinding
@Profile("dev")
public class VoucherProperties {
    private final String savePath;

    public VoucherProperties(String savePath) {
        this.savePath = savePath;
    }

    public String getSavePath() {
        return savePath;
    }
}
