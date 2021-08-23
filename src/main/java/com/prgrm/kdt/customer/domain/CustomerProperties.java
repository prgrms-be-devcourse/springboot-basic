package com.prgrm.kdt.customer.domain;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
//@ConfigurationProperties(prefix = "kdt")
public class CustomerProperties implements InitializingBean {
    @Value("${kdt.customer.blacklist-file-path}")
    private String blackListFilePath;

    public String getBlackListFilePath() {
        return blackListFilePath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        System.out.println(MessageFormat.format("BlackList path => {0}",blackListFilePath));
    }
}
