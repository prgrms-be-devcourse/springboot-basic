package com.prgms.kdtspringorder.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.prgms.kdtspringorder"})
@PropertySource(value = "application-dev.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfig {
}
