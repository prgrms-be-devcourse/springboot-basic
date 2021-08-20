package com.programmers.voucher.config;

import com.programmers.voucher.entity.voucher.factory.FixedAmountVoucherFactory;
import com.programmers.voucher.entity.voucher.factory.PercentDiscountVoucherFactory;
import com.programmers.voucher.entity.voucher.factory.VoucherFactory;
import com.programmers.voucher.repository.voucher.InMemoryVoucherRepository;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import com.programmers.voucher.service.voucher.BasicVoucherService;
import com.programmers.voucher.service.voucher.VoucherService;
import org.springframework.context.annotation.Bean;

public class ServiceConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new InMemoryVoucherRepository();
    }

    @Bean
    public VoucherService voucherService() {
        return new BasicVoucherService(voucherRepository(), fixedAmountVoucherFactory(), percentDiscountVoucherFactory());
    }

    @Bean
    public VoucherFactory fixedAmountVoucherFactory() {
        return new FixedAmountVoucherFactory();
    }

    @Bean
    public VoucherFactory percentDiscountVoucherFactory() {
        return new PercentDiscountVoucherFactory();
    }
}
