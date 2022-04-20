package org.prgrms.kdt.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "org.prgrms.kdt")
@PropertySource(value = "application.yml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
}
