package com.programmers.voucher;

import com.programmers.voucher.repository.VoucherMemoryRepository;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherMemoryRepository();
    }
}
