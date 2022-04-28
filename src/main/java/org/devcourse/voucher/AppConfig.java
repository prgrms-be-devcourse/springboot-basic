package org.devcourse.voucher;

import org.devcourse.voucher.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.devcourse.voucher.voucher", "org.devcourse.voucher.view",
        "org.devcourse.voucher.configuration", "org.devcourse.voucher.customer"})
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfig {
}
