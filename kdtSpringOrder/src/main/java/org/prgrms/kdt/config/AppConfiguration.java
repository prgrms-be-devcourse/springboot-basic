package org.prgrms.kdt.config;

import org.prgrms.kdt.repository.OrderRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

//VoucherService, OrderService, OrderRepository, VoucherRepository 의 생성에 대한 책임을 가지게 된다.
//IoC 컨테이너 역할을 하기때문에 객체들의 의존관계 설정, 객체 생성, 파괴를 담당한다.
@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        //VoucherRepository Interface, 구현 객체없이 Override.
        //Q. 구현객체 없이 Override 하는 경우가 많나? (인라인 방식을 많이 사용하나?)
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
            public void insert(Order order) {
            }
        };
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }

}