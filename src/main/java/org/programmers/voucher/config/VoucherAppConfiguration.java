package org.programmers.voucher.config;

import org.programmers.voucher.controller.VoucherController;
import org.programmers.voucher.io.ConsoleInput;
import org.programmers.voucher.io.ConsoleOutput;
import org.programmers.voucher.repository.MemoryVoucherRepository;
import org.programmers.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoucherAppConfiguration {

    @Bean
    public VoucherController voucherController(){
        return new VoucherController(new MemoryVoucherRepository(), new ConsoleInput(), new ConsoleOutput());
    }

    @Bean
    public VoucherRepository voucherRepository(){
        return new MemoryVoucherRepository();
    }
}
