package com.programmers.voucher.global.config;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public TextIO textIO() {
        return TextIoFactory.getTextIO();
    }
}
