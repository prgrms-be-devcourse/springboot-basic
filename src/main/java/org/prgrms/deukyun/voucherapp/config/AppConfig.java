package org.prgrms.deukyun.voucherapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.deukyun.voucherapp.order", "org.prgrms.deukyun.voucherapp.voucher", "org.prgrms.deukyun.voucherapp.config"})
@PropertySource("application.properties")
public class AppConfig {
}
