package org.prgrms.deukyun.voucherapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.deukyun.voucherapp.repository", "org.prgrms.deukyun.voucherapp.service"})
public class AppConfig {
}
