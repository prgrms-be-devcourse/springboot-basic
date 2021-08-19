package org.prgms;


import org.prgms.io.Io;
import org.prgms.order.Order;
import org.prgms.voucher.Voucher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan(basePackageClasses = {Order.class, Voucher.class, Io.class}) //order, voucher, Io가 속한 패키지 기준
@PropertySource("application.properties")

public class AppConfiguration {
    
}
