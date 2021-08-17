package org.prgrms.kdt.kdtspringorder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

/**
 * 각 Bean을 정의한 Configuration Meta Data
 */

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return
        new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
        };
    }

    @Bean
    public OrderRepositiry orderRepositiry() {
        return new OrderRepositiry() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepositiry());
    }

}
