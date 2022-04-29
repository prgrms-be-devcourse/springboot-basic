package org.prgrms.part1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "kdt")
public class VoucherProperties implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(VoucherProperties.class);

    private String environment;

    @Override
    public void afterPropertiesSet() {
        logger.debug("environment -> {}", environment);
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }
}
