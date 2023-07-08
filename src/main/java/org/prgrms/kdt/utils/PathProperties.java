package org.prgrms.kdt.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file-path")
public class PathProperties {
    private String origin;
    private String blacklist;

    public String getOrigin() {
        return origin;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }
}