package team.marco.voucher_management_system.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("file.path")
public record FilePathProperties(
        String blacklist,
        String voucherData) {
}
