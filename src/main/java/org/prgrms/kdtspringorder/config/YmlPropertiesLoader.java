package org.prgrms.kdtspringorder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class YmlPropertiesLoader {

    private final String blackListFilePath;
    private final String voucherFilePath;

    public YmlPropertiesLoader(@Value("${files.black-list:}") String blackListFilePath,
                               @Value("${files.voucher}") String voucherFilePath) {
        this.blackListFilePath = blackListFilePath;
        this.voucherFilePath = voucherFilePath;
    }

    public String getBlackListFilePath() {
        return this.blackListFilePath;
    }

    public String getVoucherFilePath() {
        return this.voucherFilePath;
    }
}
