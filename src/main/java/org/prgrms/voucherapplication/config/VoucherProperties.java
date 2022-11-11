package org.prgrms.voucherapplication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("voucher")
public final class VoucherProperties {
    private final String description;

    public VoucherProperties(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
