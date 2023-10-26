package com.programmers.vouchermanagement.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("test")
@ComponentScan(basePackages = "com.programmers.vouchermanagement")
@PropertySource(value = "application-test.yaml", factory = YamlPropertiesFactory.class)
public class TestConfig {
}
