package org.prgrms.kdt.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "kdt")
public class OrderProperties implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(OrderProperties.class);

    private String version;

    private Integer minimumOrderAmount;

    private List<String> supportVendors;

    private List<String> description;

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(Integer minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public List<String> getSupportVendors() {
        return supportVendors;
    }

    public void setSupportVendors(List<String> supportVendors) {
        this.supportVendors = supportVendors;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }


}
