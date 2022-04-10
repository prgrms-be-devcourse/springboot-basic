package org.prgms.management;

import org.prgms.management.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgms.management.entity", "org.prgms.management.repository", "org.prgms.management.service"})
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
}
