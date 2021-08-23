package com.prgrm.kdt;

import com.prgrm.kdt.config.YamlPropertiesFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(
        basePackages = {"com.prgrm.kdt.order", "com.prgrm.kdt.voucher", "com.prgrm.kdt.customer", "com.prgrm.kdt.config"}
)

@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
public class AppConfiguration {

}
