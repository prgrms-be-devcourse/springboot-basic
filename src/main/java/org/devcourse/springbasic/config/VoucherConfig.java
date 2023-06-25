package org.devcourse.springbasic.config;

import org.devcourse.springbasic.voucher.FixedAmountVoucher;
import org.devcourse.springbasic.voucher.PercentDiscountVoucher;
import org.devcourse.springbasic.voucher.Voucher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class VoucherConfig {

    @Bean
    public Voucher fixedAmountVoucher() {
        return new FixedAmountVoucher(UUID.randomUUID(), 10L);
    }

    @Bean
    public Voucher percentDiscountVoucher() {
        return new PercentDiscountVoucher(UUID.randomUUID(), 30L);
    }

}
