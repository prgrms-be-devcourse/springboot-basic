package org.prgms.management;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgms.management.entity", "org.prgms.management.repository",
        "org.prgms.management.service", "org.prgms.management.io"})
@PropertySource("/application.yaml")
@EnableConfigurationProperties
public class AppConfiguration {
}
