package org.prgrms.kdtspringorder;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdtspringorder.VoucherRepositoryImpl;
import org.springframework.context.annotation.PropertySource;


@ComponentScan
@Configuration
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

//    @Bean
//    public VoucherRepository voucherRepository(){
//        return new VoucherRepositoryImpl();
//    }
//    @Bean
//    public OrderRepository orderRepository(){
//        return new OrderRepository() {
//            @Override
//            public void insert(Order order) {
//
//            }
//        };
//    }
//    @Bean
//    public VoucherService voucherService(VoucherRepository voucherRepository){
//        return new VoucherService(voucherRepository);
//    }
//    @Bean
//    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository){
//        return new OrderService(voucherService,orderRepository);
//    }
}