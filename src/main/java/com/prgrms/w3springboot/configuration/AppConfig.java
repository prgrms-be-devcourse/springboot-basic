package com.prgrms.w3springboot.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(
        basePackages = {"com.prgrms.w3springboot.configuration", "com.prgrms.w3springboot.io", "com.prgrms.w3springboot.voucher", "com.prgrms.w3springboot.order", "com.prgrms.w3springboot.customer"}
)
@PropertySource(value = "application.yml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfig {
}
