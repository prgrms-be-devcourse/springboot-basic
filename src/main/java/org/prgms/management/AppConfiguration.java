package org.prgms.management;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgms.management.blacklist", "org.prgms.management.io", "org.prgms.management.voucher",
        "org.prgms.management.customer", "org.prgms.management.wallet"})
@PropertySource("/application.yaml")
@EnableConfigurationProperties
public class AppConfiguration {
}
