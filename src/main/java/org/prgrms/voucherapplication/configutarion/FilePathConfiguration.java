package org.prgrms.voucherapplication.configutarion;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "path")
public final class FilePathConfiguration {
    private String blacklist;
    private String voucherlist;

    public String getBlacklist() {
        return blacklist;
    }

    public String getVoucherlist() {
        return voucherlist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public void setVoucherlist(String voucherlist) {
        this.voucherlist = voucherlist;
    }
}
