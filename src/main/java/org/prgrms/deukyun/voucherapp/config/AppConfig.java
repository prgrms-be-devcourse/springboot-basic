package org.prgrms.deukyun.voucherapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.deukyun.voucherapp.repository", "org.prgrms.deukyun.voucherapp.service"})
@PropertySource("application.properties")
public class AppConfig {
}
