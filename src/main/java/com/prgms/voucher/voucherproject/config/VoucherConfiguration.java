package com.prgms.voucher.voucherproject.config;

import com.prgms.voucher.voucherproject.VoucherApp;
import com.prgms.voucher.voucherproject.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoucherConfiguration {

    @Bean
    public VoucherApp voucherApp(VoucherService voucherService) {
        return new VoucherApp(voucherService);
    }

}
