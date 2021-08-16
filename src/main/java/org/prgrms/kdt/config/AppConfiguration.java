package org.prgrms.kdt.config;

import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.io.IoInteraction;
import org.prgrms.kdt.repository.OrderRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.repository.VoucherRepositoryImpl;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// service, repository에 대한 생성의 책임을 가진다.
// 각 클래스간의 의존관계를 담당함
// 컴포넌트들을 생성하는 메서드를 만듬
// 빈을 정의한 configuration meta-data(설정클래스)
@Configuration
public class AppConfiguration {

    @Bean
    public IoInteraction ioInteraction() {
        return new IoInteraction();
    }
    // voucherRepository는 인터페이스엿음으로 구현체가 필요함
    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepositoryImpl();
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
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderServie() {
        return new OrderService(voucherService(), orderRepository());
    }

}
