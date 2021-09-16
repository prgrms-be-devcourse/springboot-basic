package org.prgrms.kdtspringhomework.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.kdtspringhomework.order",
                        "org.prgrms.kdtspringhomework.voucher",
                        "org.prgrms.kdtspringhomework.config",
                        "org.prgrms.kdtspringhomework.customer"}
)
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
}