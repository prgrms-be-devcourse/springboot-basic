package org.prgrms.kdtspringorder;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//import org.prgrms.kdtspringorder.VoucherRepositoryImpl;

@Configuration
@ComponentScan
public class AppConfiguration {
    @Bean(initMethod = "init")
    public Beanone beanone(){
        return new Beanone();
    }
    class Beanone implements InitializingBean{

        public void init(){
            System.out.println("[BeanOne]init called");

        }
        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("[Beanone] afterPropertiesSet called!");
        }

    }


//    public VoucherRepository voucherRepository(){
//        return new VoucherRepositoryImpl();
//    }
//
//    public OrderRepository orderRepository(){
//        return new OrderRepository() {
//            @Override
//            public Order insert(Order order) {
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