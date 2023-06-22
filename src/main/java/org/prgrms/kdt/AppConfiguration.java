package org.prgrms.kdt;

import org.prgrms.kdt.common.config.YamlPropertiesFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.common.config"})
@PropertySource("classpath:./application.properties")
public class AppConfiguration {
}
