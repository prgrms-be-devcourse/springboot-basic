package org.prgms;


import org.prgms.io.Io;
import org.prgms.order.Order;
import org.prgms.voucher.Voucher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackageClasses = {Order.class, Voucher.class}) //order, voucher가 속한 패키지 기준
public class AppConfiguration {
    @Bean
    public Io io(){
        return new Io();
    }


}
