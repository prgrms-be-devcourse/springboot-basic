package com.example.voucher_manager;

import com.example.voucher_manager.domain.repository.MemoryVoucherRepository;
import com.example.voucher_manager.domain.repository.VoucherRepository;
import com.example.voucher_manager.domain.service.VoucherService;
import com.example.voucher_manager.io.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Input consoleInput() {
        return new ConsoleInput();
    }

    @Bean
    public Output consoleOutput() {
        return new ConsoleOutput();
    }

    @Bean
    public InputValidator inputValidator(){
        return new InputValidator();
    }

    @Bean
    public VoucherRepository memoryVoucherRepository() {
        return new MemoryVoucherRepository();
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(memoryVoucherRepository());
    }

    @Bean
    public CommandOperator commandOperator(VoucherService voucherService) {
        return new CommandOperator(voucherService);
    }

    @Bean
    public Console console(Input input, Output output, InputValidator inputValidator, CommandOperator commandOperator) {
        return new Console(input, output, inputValidator, commandOperator);
    }

    @Bean
    public RunApplication runApplication(Console console) {
        return new RunApplication(console);
    }
}
