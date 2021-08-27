package org.prgrms.kdt.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.order","org.prgrms.kdt.voucher","org.prgrms.kdt.customer"})
@PropertySource(value = "classpath:application.yml", factory = YamlPropertiesFactory.class)

public class AppConfiguration {
}
