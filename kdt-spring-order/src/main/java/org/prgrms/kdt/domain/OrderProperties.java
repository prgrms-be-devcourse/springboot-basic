package org.prgrms.kdt.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "kdt")
public class OrderProperties implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(OrderProperties.class);

//    @Value("${kdt.version:v0.0.0}")
    private String version;

//    @Value("${kdt.minimum-order-amount}")
    private int minimumOrderAmount;

//    @Value("${kdt.support-vendors}")
    private List<String> supportVendors;

    private String description;

//    @Value("${JAVA_HOME}")
    private String javaHome = "임시방편 -> JAVA_HOME 값 정의하기";

    @Override
    public void afterPropertiesSet() throws Exception {
//        System.out.println(MessageFormat.format("[orderProperties] version -> {0}",version));
//        System.out.println(MessageFormat.format("[orderProperties] minimumOrderAmount -> {0}",minimumOrderAmount));
//        System.out.println(MessageFormat.format("[orderProperties] supportVendors -> {0}",supportVendors));
//        System.out.println(MessageFormat.format("[orderProperties] description -> {0}",description));
//        System.out.println(MessageFormat.format("[orderProperties] JAVA_HOME -> {0}",javaHome));

        // logger를 이용해서 출력하기 - debug 모드
        logger.warn("[orderProperties] version -> {}",version);
        logger.warn("[orderProperties] minimumOrderAmount -> {}",minimumOrderAmount);
        logger.warn("[orderProperties] supportVendors -> {}",supportVendors);
        logger.warn("[orderProperties] JAVA_HOME -> {}",javaHome);
//        logger.debug("[orderProperties] description -> {}",description);

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

    public String getJavaHome() {
        return javaHome;
    }

    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }
}
