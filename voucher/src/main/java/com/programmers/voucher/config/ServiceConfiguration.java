package com.programmers.voucher.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.programmers.voucher")
@EnableConfigurationProperties
public class ServiceConfiguration {

}
