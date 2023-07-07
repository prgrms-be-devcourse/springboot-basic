package com.example.voucher.config;

import com.example.voucher.CommandHandler;
import com.example.voucher.repository.InMemoryVoucherRepository;
import com.example.voucher.repository.VoucherRepository;
import com.example.voucher.ui.Input;
import com.example.voucher.ui.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoucherConfig {

    @Bean
    public CommandHandler commandHandler() {
        return new CommandHandler(input(), output());
    }

    @Bean
    public VoucherRepository voucherRepository() {
        return inMemoryVoucherRepository();
    }

    @Bean
    public Input input() {
        return new Input();
    }

    @Bean
    public Output output() {
        return new Output();
    }

    @Bean
    public InMemoryVoucherRepository inMemoryVoucherRepository() {
        return new InMemoryVoucherRepository();
    }
}
