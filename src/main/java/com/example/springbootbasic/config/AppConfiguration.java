package com.example.springbootbasic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "pro")
public class AppConfiguration {
    private String voucherCsvResource;
    private String customerCsvResource;

    public AppConfiguration(String voucherCsvResource, String customerCsvResource) {
        this.voucherCsvResource = voucherCsvResource;
        this.customerCsvResource = customerCsvResource;
    }

    public String getVoucherCsvResource() {
        return voucherCsvResource;
    }

    public String getCustomerCsvResource() {
        return customerCsvResource;
    }
}
