package com.programmers.voucher.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.programmers.voucher")
@PropertySource(value = "application.yml", factory = YamlPropertySourceFactory.class)
@EnableConfigurationProperties(ApplicationMessages.class)
@ConfigurationPropertiesScan(basePackages = "com.programmers.voucher.config")
public class ServiceConfiguration {

}
