package org.prgrms.kdt;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource(value ="application.yaml", factory = YamlPropertiesFactory.class)
public class AppConfiguration {

}
