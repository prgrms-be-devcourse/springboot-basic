package org.prgrms.kdt.configuration;

import org.prgrms.kdt.configuration.YamlPropertiesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {
        "org.prgrms.kdt.customer",
        "org.prgrms.kdt.voucher",
        "org.prgrms.kdt.order",
        "org.prgrms.kdt.configuration",
        "org.prgrms.kdt.engine"
})
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
    // 의존 관계 형성
    @Autowired
    MessageSource messageSource;
}
