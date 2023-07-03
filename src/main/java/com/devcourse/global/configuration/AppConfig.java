package com.devcourse.global.configuration;

import com.devcourse.voucher.domain.repository.MemoryVoucherRepository;
import com.devcourse.voucher.domain.repository.VoucherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }
}
