package com.example.springbootbasic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "csv")
public class CsvProperties {
    private final String voucherCsvResource;
    private final String customerCsvResource;

    public CsvProperties(String voucherCsvResource, String customerCsvResource) {
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
