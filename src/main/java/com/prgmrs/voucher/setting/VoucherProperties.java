package com.prgmrs.voucher.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "system.voucher")
public class VoucherProperties {
    private long maximumFixedAmount;

    public long getMaximumFixedAmount() {
        return maximumFixedAmount;
    }

    public void setMaximumFixedAmount(long maximumFixedAmount) {
        this.maximumFixedAmount = maximumFixedAmount;
    }
}
