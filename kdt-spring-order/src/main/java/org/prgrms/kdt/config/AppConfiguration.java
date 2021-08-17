package org.prgrms.kdt.config;

import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.repository.OrderRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.repository.VoucherRepositoryImpl;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository(){
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
    public VoucherService voucherService(VoucherRepository voucherRepository){
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository){
        return new OrderService(voucherService, orderRepository);
    }
}
