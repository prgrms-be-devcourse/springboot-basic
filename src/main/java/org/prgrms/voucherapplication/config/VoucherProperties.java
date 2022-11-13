package org.prgrms.voucherapplication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("voucher")
public final class VoucherProperties {
    private final String description;
    private final String filePath;

    public VoucherProperties(String description, String filePath) {
        this.description = description;
        this.filePath = filePath;
    }

    public String getDescription() {
        return description;
    }

    public String getFilePath() {
        return filePath;
    }
}
