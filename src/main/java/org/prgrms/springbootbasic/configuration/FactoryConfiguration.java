package org.prgrms.springbootbasic.configuration;

import org.prgrms.springbootbasic.factory.FixedAmountVoucherFactory;
import org.prgrms.springbootbasic.factory.PercentAmountVoucherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryConfiguration {
    @Bean
    public FixedAmountVoucherFactory fixedAmountVoucherFactory() {
        return new FixedAmountVoucherFactory();
    }

    @Bean
    public PercentAmountVoucherFactory percentAmountVoucherFactory() {
        return new PercentAmountVoucherFactory();
    }
}
