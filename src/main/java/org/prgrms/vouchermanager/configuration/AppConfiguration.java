package org.prgrms.vouchermanager.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties
@ComponentScan(basePackages = {"org.prgrms.vouchermanager"})
@Configuration
public class AppConfiguration {

}
