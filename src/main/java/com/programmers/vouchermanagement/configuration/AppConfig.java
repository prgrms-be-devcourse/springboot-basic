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
    // Conversation:
    // Injecting logger to where it would be used
    //                  VS
    // Static logger to be used in wherever willing to use
    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(VoucherManagementApplication.class);
    }

    @Bean
    public TextIO textIO() {
        return TextIoFactory.getTextIO();
    }
}
