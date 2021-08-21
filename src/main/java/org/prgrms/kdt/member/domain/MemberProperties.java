package org.prgrms.kdt.member.domain;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kdt.member")
public class MemberProperties implements InitializingBean {
    private String csvFilePath;

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
