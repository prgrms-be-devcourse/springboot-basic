package com.example.springbootbasic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;

@ConstructorBinding
@ConfigurationProperties(prefix = "csv")
@Profile("csv")
public class CsvProperties {
    private String voucherCsvResource;
    private String customerCsvResource;

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
