package com.prgrm.kdt.customer.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Component;

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
//        logger.debug("blackListFilePath => {}", blackListFilePath);
    }
}
