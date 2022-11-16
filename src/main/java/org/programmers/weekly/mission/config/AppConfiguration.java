package org.programmers.weekly.mission.config;

import org.programmers.weekly.mission.util.io.Console;
import org.programmers.weekly.mission.util.io.Input;
import org.programmers.weekly.mission.util.io.Output;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.programmers.weekly.mission.domain.voucher",
        "org.programmers.weekly.mission.domain.customer",
        "org.programmers.weekly.mission.config"})
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

    @Bean
    public Input input() {
        return new Console();
    }

    @Bean
    public Output output() {
        return new Console();
    }

}
