package org.prgrms.kdt.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "kdt")
public class OrderProperties implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(OrderProperties.class);
    private String version;

    private String minimunOrderAmount;

    private List<String> supportVendors;

    private String description;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug(MessageFormat.format("version -> {0}", version));
        logger.debug(MessageFormat.format("minimunOrderAmount -> {0}", minimunOrderAmount));
        logger.debug(MessageFormat.format("supportVendors -> {0}", supportVendors));
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMinimunOrderAmount() {
        return minimunOrderAmount;
    }

    public void setMinimunOrderAmount(String minimunOrderAmount) {
        this.minimunOrderAmount = minimunOrderAmount;
    }

    public List<String> getSupportVendors() {
        return supportVendors;
    }

    public void setSupportVendors(List<String> supportVendors) {
        this.supportVendors = supportVendors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
