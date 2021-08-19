package org.prgrms.kdt.config;

import org.prgrms.kdt.controller.InputController;
import org.prgrms.kdt.controller.OutputController;
import org.prgrms.kdt.helper.MessageHelper;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.prgrms.kdt.repository.OrderRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//VoucherService, OrderService, OrderRepository, VoucherRepository 의 생성에 대한 책임을 가지게 된다.
//IoC 컨테이너 역할을 하기때문에 객체들의 의존관계 설정, 객체 생성, 파괴를 담당한다.
@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
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

    @Bean
    public InputController inputController() {
        return new InputController();
    }

    @Bean
    public OutputController outputController() {
        return new OutputController();
    }

    @Bean
    public MessageHelper messageHelper() {
        return new MessageHelper();
    }

}