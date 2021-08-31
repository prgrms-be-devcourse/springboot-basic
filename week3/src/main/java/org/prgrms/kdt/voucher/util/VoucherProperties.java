package org.prgrms.kdt.voucher.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "voucher")
public class VoucherProperties implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(VoucherProperties.class);

    private String version;

    private List<String> description;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("[VoucherProperties] version -> {}", version);
        logger.debug("[VoucherProperties] minimumOrderAmount -> {}", description);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }


}
