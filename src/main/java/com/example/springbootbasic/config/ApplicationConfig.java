package com.example.springbootbasic.config;

import com.example.springbootbasic.console.input.ConsoleInput;
import com.example.springbootbasic.console.output.ConsoleOutput;
import com.example.springbootbasic.controller.MainController;
import com.example.springbootbasic.controller.VoucherController;
import com.example.springbootbasic.repository.MemoryVoucherRepository;
import com.example.springbootbasic.repository.VoucherRepository;
import com.example.springbootbasic.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ConsoleInput consoleInput() {
        return new ConsoleInput();
    }

    @Bean
    public ConsoleOutput consoleOutput() {
        return new ConsoleOutput();
    }

    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

    @Bean
    public VoucherController voucherController(VoucherService voucherService) {
        return new VoucherController(voucherService);
    }

    @Bean
    public MainController mainController(ConsoleInput consoleInput, ConsoleOutput consoleOutput, VoucherController voucherController) {
        return new MainController(consoleInput, consoleOutput, voucherController);
    }
}
