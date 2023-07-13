package com.example.voucher.config;

import com.example.voucher.CommandHandler;
import com.example.voucher.ui.ConsoleView;
import com.example.voucher.util.UUIDGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoucherConfig {

    @Bean
    public CommandHandler commandHandler() {
        return new CommandHandler(consoleView(), uuidGenerator());
    }

    @Bean
    public ConsoleView consoleView() {
        return new ConsoleView();
    }

    @Bean
    public UUIDGenerator uuidGenerator() {
        return new UUIDGenerator();
    }
}
