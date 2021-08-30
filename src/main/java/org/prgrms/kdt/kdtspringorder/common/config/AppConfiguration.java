package org.prgrms.kdt.kdtspringorder.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 각 Bean을 정의한 Configuration Meta Data
 */

//@Configuration
//@ComponentScan(
//        basePackages = { "org.prgrms.kdt.kdtspringorder" }
//)
//@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
//@EnableConfigurationProperties // @ConfigurationProperties를 사용하려면 이걸 써줘야함
//public class AppConfiguration {
//
////    @Bean
////    public VoucherRepository voucherRepository() {
////        return new MemoryVoucherRepository();
////    }
////
////    @Bean
////    public OrderRepository orderRepositiry() {
////        return new OrderRepository() {
////            @Override
////            public void insert(Order order) {
////
////            }
////        };
////    }
////
////    @Bean
////    public VoucherService voucherService() {
////        return new VoucherService(voucherRepository());
////    }
////
////    @Bean
////    public OrderService orderService() {
////        return new OrderService(voucherService(), orderRepositiry());
////    }
////
////    @Bean
////    public VoucherCommandLine voucherCommandLine() {
////        return new VoucherCommandLine(voucherService(), new ConsoleInput(), new ConsoleOutput());
////    }
//
//
//}
