package com.example.springbootbasic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pro")
public class AppConfiguration {
    private String voucherCsvResource;

    public String getVoucherCsvResource() {
        return voucherCsvResource;
    }

    public void setVoucherCsvResource(String voucherCsvResource) {
        this.voucherCsvResource = voucherCsvResource;
    }
}
