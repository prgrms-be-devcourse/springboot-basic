package org.prgms.voucheradmin.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "voucher-admin")
public class VoucherAdminProperties {
    private String voucherFilePath;

    private String blacklistFilePath;

    public String getVoucherFilePath() {
        return voucherFilePath;
    }

    public void setVoucherFilePath(String voucherFilePath) {
        this.voucherFilePath = voucherFilePath;
    }

    public String getBlacklistFilePath() {
        return blacklistFilePath;
    }

    public void setBlacklistFilePath(String blacklistFilePath) {
        this.blacklistFilePath = blacklistFilePath;
    }
}
