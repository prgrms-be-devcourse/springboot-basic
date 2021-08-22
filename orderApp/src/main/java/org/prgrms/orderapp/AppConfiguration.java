package org.prgrms.orderapp;


import org.prgrms.orderapp.config.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource(value = "application.yml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

}
