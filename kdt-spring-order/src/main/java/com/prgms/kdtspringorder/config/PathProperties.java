package com.prgms.kdtspringorder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kdt.path")
public class PathProperties {
    private String voucherList;
    private String customerBlacklist;

    public String getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(String voucherList) {
        this.voucherList = voucherList;
    }

    public String getCustomerBlacklist() {
        return customerBlacklist;
    }

    public void setCustomerBlacklist(String customerBlacklist) {
        this.customerBlacklist = customerBlacklist;
    }
}
