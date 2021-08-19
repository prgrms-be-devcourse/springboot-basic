package org.prgrms.orderApp;

import org.prgrms.orderApp.model.order.Order;
import org.prgrms.orderApp.repository.OrderRepository;
import org.prgrms.orderApp.repository.TempVoucherRepository;
import org.prgrms.orderApp.repository.VoucherRepository;
import org.prgrms.orderApp.service.OrderService;
import org.prgrms.orderApp.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }

    @Bean
    public VoucherRepository voucherRepository() {
        return new TempVoucherRepository();
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }


}
