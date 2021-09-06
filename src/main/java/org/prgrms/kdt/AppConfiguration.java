package org.prgrms.kdt;

import org.prgrms.kdt.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {
        "org.prgrms.kdt.customer",
        "org.prgrms.kdt.voucher",
//        "org.prgrms.kdt.order",
        "org.prgrms.kdt.configuration"
})
@PropertySource(value = "application-local.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
    // 의존 관계 형성
}
