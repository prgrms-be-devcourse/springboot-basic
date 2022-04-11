package org.prgrms.kdt.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan( basePackages = {
        "org.prgrms.kdt.config",
        "org.prgrms.kdt.voucher",
        "org.prgrms.kdt.blacklist"
})

@EnableConfigurationProperties
public class AppConfiguration { }