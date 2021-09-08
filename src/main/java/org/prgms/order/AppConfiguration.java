package org.prgms.order;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {
        "org.prgms.order.order.*",
        "org.prgms.order.customer.*",
        "org.prgms.order.voucher.*",
        "org.prgms.order.configuration"})
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
}
