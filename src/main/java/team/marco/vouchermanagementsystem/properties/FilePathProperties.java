package team.marco.vouchermanagementsystem.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("file.path")
public record FilePathProperties(
        String blacklist,
        String voucherData) {
}
