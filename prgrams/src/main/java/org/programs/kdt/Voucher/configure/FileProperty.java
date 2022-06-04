package org.programs.kdt.Voucher.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "filepath")
@Configuration
@Getter
@Setter
public class FileProperty {
    private String voucher;

    private String blacklist;
}
