package org.prgrms.kdt;

import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderRepository;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

/**
 * AppConfiguration은 VoucherService, OrderService, OrderRepo, VoucherRepo 객체의 생성에 대한 책임을 갖는다.
 * 또한 각각의 Service, Repository객체 간의 의존관계를 형성시킨다. (IoC컨테이너 역할)
 * @Configuration은 스프링에게 Configuration Metadata파일(bean을 정의한 도면)임을 알려주기 위해 사용한다.
 * 즉 AppConfiguration은 애플리케이션의 설정파일(클래스)이다. (bean객체들의 일종의 도면을 정의하는 파일)
 */
@Configuration
public class AppConfiguration {
    /**
     * AppConfiguration는 일반클래스가 아니라 bean객체를 정의한 Configuration Metadata이고
     * 이를 의미하기 위해 @Bean 사용
     */
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
            public void insert(Order order) {
                // TODO: 영속성 추가
            }
        };
    }

    /**
     * IoC컨테이너는 실제로 (익명)객체를 전달함으로써 객체들 간의 의존관계를 형성시킨다.
     */
    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    } // bean의 메소드에 parameter로 define함으로써 실질적인 Dependency resolution이 이루어 질 수 있다.

    /*
        @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }
     */
}
