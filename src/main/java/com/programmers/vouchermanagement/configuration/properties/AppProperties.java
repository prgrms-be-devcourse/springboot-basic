package com.programmers.vouchermanagement.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "file")
public class AppProperties {
    private final Resources resources;
    private final Domains domains;

    @ConstructorBinding
    public AppProperties(Resources resources, Domains domains) {
        this.resources = resources;
        this.domains = domains;
    }

    public String getCustomerFilePath() {
        return resources.path() + domains.customer().fileName();
    }

    public String getVoucherFilePath() {
        return resources.path() + domains.voucher().fileName();
    }

    public String getCustomerBuildFilePath() {
        return resources.buildPath() + domains.customer().fileName();
    }

    public String getVoucherBuildFilePath() {
        return resources.buildPath() + domains.voucher().fileName();
    }
}
