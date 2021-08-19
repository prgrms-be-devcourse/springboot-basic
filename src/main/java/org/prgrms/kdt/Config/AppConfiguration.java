package org.prgrms.kdt.Config;

import org.prgrms.kdt.Entity.Order;
import org.prgrms.kdt.Repository.OrderRepository;
import org.prgrms.kdt.Repository.VoucherRepository;
import org.prgrms.kdt.Service.OrderService;
import org.prgrms.kdt.Service.VoucherService;
import org.prgrms.kdt.Repository.VoucherRepoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository(){
        return new VoucherRepoImpl();
    }

    @Bean
    public OrderRepository orderRepository(){
        return new OrderRepository() {
            @Override
            public void insert(Order order) {
            }
        };
    }

    // 생성자 주입말고도 parameter define해서 dependency resolution 가능.
    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository){
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository){
        return new OrderService(voucherService, orderRepository);
    }
}
