package org.prgrms.kdt.engine.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "kdt")
public class OrderProperties implements InitializingBean {

    private String version;
    private String description;
    private List<String> supportVendors;

    @Value("${PWD}")
    private String PWD;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(version);
        System.out.println(description);
        System.out.println(supportVendors);
        System.out.println(PWD);
    }

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
}
