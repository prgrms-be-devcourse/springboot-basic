package org.programmers.springboot.basic.config;

import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.service.validate.FixedAmountVoucherValidator;
import org.programmers.springboot.basic.domain.voucher.service.validate.PercentDiscountVoucherValidator;
import org.programmers.springboot.basic.domain.voucher.service.validate.ValidateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {
        "org.programmers.springboot.basic",
        "org.programmers.springboot.basic.domain.voucher.mapper",
        "org.programmers.springboot.basic.domain.customer.mapper"
})
public class VoucherConfig {

    @Bean
    public ValidateHandler PercentVoucherValidator()  {
        return new PercentDiscountVoucherValidator();
    }

    @Bean
    public ValidateHandler FixedVoucherValidator() {
        return new FixedAmountVoucherValidator();
    }

    @Bean
    public Map<VoucherType, ValidateHandler> validateHandlers() {
        Map<VoucherType, ValidateHandler> handler = new HashMap<>();
        handler.put(VoucherType.FIXED, FixedVoucherValidator());
        handler.put(VoucherType.PERCENT, PercentVoucherValidator());
        return handler;
    }
}
