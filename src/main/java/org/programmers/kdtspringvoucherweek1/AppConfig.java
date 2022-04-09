package org.programmers.kdtspringvoucherweek1;

import org.programmers.kdtspringvoucherweek1.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.programmers.kdtspringvoucherweek1.voucher", "org.programmers.kdtspringvoucherweek1.io",
        "org.programmers.kdtspringvoucherweek1.blacklist"})
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfig {

}
