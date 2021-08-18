package org.prgms.w3d1.configuration;


import org.prgms.w3d1.model.order.Order;
import org.prgms.w3d1.model.order.OrderService;
import org.prgms.w3d1.model.voucher.VoucherService;
import org.prgms.w3d1.repository.OrderRepository;
import org.prgms.w3d1.repository.VoucherRepository;
import org.prgms.w3d1.repository.VoucherRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository(){
        return new VoucherRepositoryImpl();
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
    public VoucherService voucherService(){
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderService(){
        return new OrderService(voucherService(), orderRepository());
    }
}
