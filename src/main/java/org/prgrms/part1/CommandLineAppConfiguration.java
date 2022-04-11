package org.prgrms.part1;

import org.prgrms.part1.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class CommandLineAppConfiguration {
}
