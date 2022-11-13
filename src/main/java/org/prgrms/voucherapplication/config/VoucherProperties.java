package org.prgrms.voucherapplication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("voucher")
public final class VoucherProperties {
    private final String description;
    private final String blacklistFilePath;

    public VoucherProperties(String description, String blakclistFilePath) {
        this.description = description;
        this.blacklistFilePath = blakclistFilePath;
    }

    public String getDescription() {
        return description;
    }

    public String getBlacklistFilePath() {
        return blacklistFilePath;
    }
}
