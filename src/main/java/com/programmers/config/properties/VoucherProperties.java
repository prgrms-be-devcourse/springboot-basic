package com.programmers.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "file.voucher")
@ConstructorBinding
public class VoucherProperties {
    private final String savePath;

    public VoucherProperties(String savePath) {
        this.savePath = savePath;
    }

    public String getSavePath() {
        return savePath;
    }
}
