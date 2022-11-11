package org.prgms.springbootbasic.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@ConfigurationProperties(prefix = "file.voucher")
@Profile("dev")
public record FileVoucherConfig(String fileName) {
}
