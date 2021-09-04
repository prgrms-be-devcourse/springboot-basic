package org.prgrms.kdt.devcourse;

import org.prgrms.kdt.devcourse.config.YamlPropertiesFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:application.yaml")
public class AppConfiguration {

}
