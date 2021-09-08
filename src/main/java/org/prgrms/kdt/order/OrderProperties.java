package org.prgrms.kdt.order;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "kdt")
public class OrderProperties implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(OrderProperties.class);

    //@Value("v1.1.1")
    //@Value("${kdt.version}:v0.0.0")  // 키 전달 하고 : 뒤를 통해 없으면 해당 값으로 대체
    private String version;

    //@Value("0")
    //@Value("${kdt.minimum-order-amount}")
    private int minimumOrderAmount;

    //@Value("a, b, c")
    //@Value("${kdt.support-vendors}")
    private List<String> supportVendors;

    private String description;

    @Value("${JAVA_HOME}") // 환경변수 값도 사용할 수 있음
    private String javahome;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("[OrderProperties] version : {}", version);
        logger.debug("[OrderProperties] minimumOrderAmount : {}", minimumOrderAmount);
        logger.debug("[OrderProperties] supportVendors : {}", supportVendors);
        logger.debug("[OrderProperties] javahome : {}", javahome);

    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(int minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
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

    public String getJavahome() {
        return javahome;
    }

}


