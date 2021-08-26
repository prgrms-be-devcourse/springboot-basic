package org.prgrms.orderApp.config.component;

import org.prgrms.orderApp.config.propertiesFile.YamlPropertiesFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.orderApp.application",
        "org.prgrms.orderApp.domain",
        "org.prgrms.orderApp.infrastructure.impl",
       })
@PropertySource("application.properties")
//@PropertySource(value="application.yaml", factory = YamlPropertiesFactory.class)
public class AppConfiguration {

}
