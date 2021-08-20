package org.prgrms.dev.config;


import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.order.domain.Order;
import org.prgrms.dev.order.repository.OrderRepository;
import org.prgrms.dev.voucher.repository.VoucherRepository;
import org.prgrms.dev.order.service.OrderService;
import org.prgrms.dev.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

/**
 * - 주요 객체에 대해서 생성과 관계 설정을 하는 클래스
 * - service, repository 에 대한 생성의 책임을 가진다.
 * - 각각의 service 와 repository 간의 의존 관계를 맺는 역할
 * <p>
 * - OrderService ↔️ OrderRepository 의 관계와
 *   OrderService ↔️ VoucherService  의 관계를
 *   OrderContext 에서 실제 객체를 전달함으로써 만든 것
 * <p>
 * - Bean을 정의한 Configuration Metadata
 * - 설정 클래스 !!
 */

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
        };
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public Order create(Order order) {
                return null;
            }
        };
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderServie() {
        return new OrderService(voucherService(), orderRepository());
    }

}
