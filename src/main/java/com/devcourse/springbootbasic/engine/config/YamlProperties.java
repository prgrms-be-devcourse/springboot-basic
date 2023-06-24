package com.devcourse.springbootbasic.engine.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;

@Configuration
@ConfigurationProperties(prefix = "settings")
public class YamlProperties implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(YamlProperties.class);

    private String version;
    private String voucherRecordPath;
    private String blackCustomerPath;
    private String description;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug(MessageFormat.format("This Version of Voucher Application : {0}", version));
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVoucherRecordPath() {
        return voucherRecordPath;
    }

    public void setVoucherRecordPath(String voucherRecordPath) {
        this.voucherRecordPath = voucherRecordPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBlackCustomerPath() {
        return blackCustomerPath;
    }

    public void setBlackCustomerPath(String blackCustomerPath) {
        this.blackCustomerPath = blackCustomerPath;
    }
}
