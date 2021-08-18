package org.prgrms.kdt;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
//@ComponentScan(basePackageClasses = {Order.class, Voucher.class}) // 스캔 범위 지정
//@PropertySource("application.properties")
public class AppConfiguration {
}
