package com.programmers.vouchermanagement.configuration;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("console")
@Configuration
public class ConsoleConfig {
    @Bean
    public TextIO textIO() {
        return TextIoFactory.getTextIO();
    }
}
