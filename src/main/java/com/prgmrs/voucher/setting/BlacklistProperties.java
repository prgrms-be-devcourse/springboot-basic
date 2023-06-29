package com.prgmrs.voucher.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "system.blacklist")
public class BlacklistProperties {
    private boolean blacklistAllow;
    private boolean blacklistShowId;

    public boolean isBlacklistAllow() {
        return blacklistAllow;
    }

    public void setBlacklistAllow(boolean blacklistAllow) {
        this.blacklistAllow = blacklistAllow;
    }

    public boolean isBlacklistShowId() {
        return blacklistShowId;
    }

    public void setBlacklistShowId(boolean blacklistShowId) {
        this.blacklistShowId = blacklistShowId;
    }
}
