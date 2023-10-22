package org.prgrms.kdt.app.configuration;

import org.prgrms.kdt.app.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(
    basePackages = {"org.prgrms.kdt.app", "org.prgrms.kdt.user", "org.prgrms.kdt.voucher"}
)
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
}

