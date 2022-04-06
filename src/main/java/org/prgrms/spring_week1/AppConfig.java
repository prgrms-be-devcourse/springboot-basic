package org.prgrms.spring_week1;

import org.prgrms.spring_week1.repositories.OrderMemoryRepository;
import org.prgrms.spring_week1.repositories.OrderRepository;
import org.prgrms.spring_week1.repositories.VoucherMemoryRepository;
import org.prgrms.spring_week1.repositories.VoucherRepository;
import org.prgrms.spring_week1.services.OrderService;
import org.prgrms.spring_week1.services.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public VoucherRepository voucherRepository(){
        return new VoucherMemoryRepository();
    }

    @Bean
    public OrderRepository oerderRepository(){
        return new OrderMemoryRepository();
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository){
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository){
        return new OrderService(orderRepository, voucherService);
    }
}
