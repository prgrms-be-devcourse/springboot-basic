package org.prgrms.orderApp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.orderApp"})
@PropertySource("application.properties")
public class AppConfigration {
}
