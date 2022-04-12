package org.prgrms.spring_week1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppConfig {

//    @Bean
//    public VoucherRepository voucherRepository(){
//        return new VoucherMemoryRepository();
//    }
//
//    @Bean
//    public OrderRepository orderRepository(){
//        return new OrderMemoryRepository();
//    }
//
//    @Bean
//    public VoucherService voucherService(VoucherRepository voucherRepository){
//        return new VoucherService(voucherRepository);
//    }
//
//    @Bean
//    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository){
//        return new OrderService(orderRepository, voucherService);
//    }
}
