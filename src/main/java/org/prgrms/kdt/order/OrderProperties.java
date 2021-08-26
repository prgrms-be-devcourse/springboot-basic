package org.prgrms.kdt.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
public class OrderProperties implements InitializingBean {

    @Value("${kdt.version:v0.0.0}")
    private String version;

    @Value("${kdt.minimum-order-amount")
    private int minimumOrderAmount;

    @Value("${kdt.support-vendors")
    private List<String> supportVendors;

    @Value("${JAVE_HOME}")
    private String javaHome;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(MessageFormat.format("[OrderProperties] version minimumOrderAmount supportVendors -> {0}",
                version + " " + minimumOrderAmount + " " + supportVendors));
    }
}
