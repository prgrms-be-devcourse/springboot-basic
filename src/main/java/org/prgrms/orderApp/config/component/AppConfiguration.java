package org.prgrms.orderApp.config.component;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.orderApp.service",
        "org.prgrms.orderApp.domain",
        "org.prgrms.orderApp.infrastructure.repository",
       })
@PropertySource("application.properties")
//@PropertySource(value="application.yaml", factory = YamlPropertiesFactory.class)
public class AppConfiguration {

}
