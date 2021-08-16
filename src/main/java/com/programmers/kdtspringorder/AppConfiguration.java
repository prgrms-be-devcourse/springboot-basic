package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.order.Order;
import com.programmers.kdtspringorder.order.OrderRepository;
import com.programmers.kdtspringorder.order.OrderService;
import com.programmers.kdtspringorder.voucher.factory.VoucherFactory;
import com.programmers.kdtspringorder.voucher.repository.VoucherMemoryRepository;
import com.programmers.kdtspringorder.voucher.repository.VoucherRepository;
import com.programmers.kdtspringorder.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository(){
        return new VoucherMemoryRepository();
    }

    @Bean
    public OrderRepository orderRepository(){
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    @Bean
    public VoucherFactory voucherFactory(){
        return new VoucherFactory();
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory){
        return new VoucherService(voucherRepository, voucherFactory);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }
}
