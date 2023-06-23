package com.devcourse.springbootbasic.engine.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;

@Configuration
@ConfigurationProperties(prefix = "settings")
public class YamlProperties implements InitializingBean {
    private String version;
    private String voucherRecordPath;
    private String blackCustomerPath;
    private String description;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(MessageFormat.format("This Version of Voucher Application : {0}", version));
    }

    public String getVersion() {
        return version;
    }

    public String getVoucherRecordPath() {
        return voucherRecordPath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getBlackCustomerPath() {
        return blackCustomerPath;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setVoucherRecordPath(String voucherRecordPath) {
        this.voucherRecordPath = voucherRecordPath;
    }

    public void setBlackCustomerPath(String blackCustomerPath) {
        this.blackCustomerPath = blackCustomerPath;
    }
}
