package org.programmers.weekly.mission.config;

import org.programmers.weekly.mission.util.io.Console;
import org.programmers.weekly.mission.util.io.Input;
import org.programmers.weekly.mission.util.io.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
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
