package org.prgrms.voucherapplication.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("file-path")
public final class VoucherProperties {
    private final String voucher;
    private final String blacklist;

    public VoucherProperties(String voucher, String blacklist) {
        this.voucher = voucher;
        this.blacklist = blacklist;
    }

    public String getVoucher() {
        return voucher;
    }

    public String getBlacklist() {
        return blacklist;
    }
}
