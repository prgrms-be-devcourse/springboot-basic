package com.programmers.springbasic.global.config;

import com.programmers.springbasic.domain.io.IOConsole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IOConfig {
    @Bean
    IOConsole ioConsole() {
        return new IOConsole();
    }
}
