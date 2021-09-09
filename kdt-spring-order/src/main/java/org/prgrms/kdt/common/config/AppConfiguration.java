package org.prgrms.kdt.common.config;

import org.prgrms.kdt.view.cmd.CommandLineView;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "org.prgrms.kdt")
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
    @Bean
    public CommandLineView commandLineView(){
        return new CommandLineView();
    }
}

