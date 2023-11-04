package org.programmers.springboot.basic.config;

import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.service.validate.FixedAmountVoucherValidationStrategy;
import org.programmers.springboot.basic.domain.voucher.service.validate.PercentDiscountVoucherValidationStrategy;
import org.programmers.springboot.basic.domain.voucher.service.validate.VoucherValidationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class VoucherConfig {

    @Bean
    public FixedAmountVoucherValidationStrategy fixedAmountVoucherValidator() {
        return new FixedAmountVoucherValidationStrategy();
    }

    @Bean
    public PercentDiscountVoucherValidationStrategy percentDiscountVoucherValidator() {
        return new PercentDiscountVoucherValidationStrategy();
    }

    @Bean
    public Map<VoucherType, VoucherValidationStrategy> validationStrategyMap() {
        Map<VoucherType, VoucherValidationStrategy> handler = new HashMap<>();
        handler.put(VoucherType.FIXED, fixedAmountVoucherValidator());
        handler.put(VoucherType.PERCENT, percentDiscountVoucherValidator());
        return handler;
    }
}
