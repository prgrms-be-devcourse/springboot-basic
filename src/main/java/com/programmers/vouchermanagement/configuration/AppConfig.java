package com.programmers.vouchermanagement.configuration;

import com.programmers.vouchermanagement.VoucherManagementApplication;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(VoucherManagementApplication.class);
    }

    @Bean
    public TextIO textIO() {
        return TextIoFactory.getTextIO();
    }
}
