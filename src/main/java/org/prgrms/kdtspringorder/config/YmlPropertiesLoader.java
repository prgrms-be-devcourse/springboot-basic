package org.prgrms.kdtspringorder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Objects;

// SpringBoot를 이용하여 Property 를 POJO로
@ConstructorBinding
@ConfigurationProperties(prefix = "files")
public class YmlPropertiesLoader {

    private final String blackListFilePath;
    private final String voucherFilePath;

    public YmlPropertiesLoader(String blackListFilePath, String voucherFilePath) {
        Objects.requireNonNull(blackListFilePath);
        Objects.requireNonNull(voucherFilePath);

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
