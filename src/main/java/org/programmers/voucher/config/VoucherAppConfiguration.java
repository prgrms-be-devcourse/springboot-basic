package org.programmers.voucher.config;

import org.programmers.voucher.controller.VoucherController;
import org.programmers.voucher.io.ConsoleInput;
import org.programmers.voucher.io.ConsoleOutput;
import org.programmers.voucher.repository.MemoryVoucherRepository;
import org.programmers.voucher.repository.VoucherRepository;
import org.programmers.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoucherAppConfiguration {

//    @Bean
//    public VoucherService voucherService() {
//        return new VoucherService(new MemoryVoucherRepository());
//    }
//
//    @Bean
//    public VoucherController voucherController() {
//        return new VoucherController(new VoucherService(new MemoryVoucherRepository()), new ConsoleInput(), new ConsoleOutput());
//    }
//
//    @Bean
//    public VoucherRepository voucherRepository() {
//        return new MemoryVoucherRepository();
//    }
}
