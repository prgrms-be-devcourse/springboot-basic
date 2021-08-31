package org.prgms.order;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {
        "org.prgms.order.order.*",
        "org.prgms.order.customer.*",
        "org.prgms.order.voucher.*"})
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
public class AppConfiguration {
}
