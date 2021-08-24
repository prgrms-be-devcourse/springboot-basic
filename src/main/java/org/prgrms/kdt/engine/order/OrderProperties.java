package org.prgrms.kdt.engine.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "kdt")
public class OrderProperties implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(OrderProperties.class);
    private String version;
    private String description;
    private List<String> supportVendors;


    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getSupportVendors() {
        return supportVendors;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSupportVendors(List<String> supportVendors) {
        this.supportVendors = supportVendors;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("OrderProperties version -> {}", version);
        logger.debug("OrderProperties version -> {}", description);
        logger.debug("OrderProperties version -> {}", supportVendors);
    }
}
