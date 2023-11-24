package team.marco.voucher_management_system.console_app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("file.path")
public record FilePathProperties(
        String blacklist,
        String voucherData) {
}
