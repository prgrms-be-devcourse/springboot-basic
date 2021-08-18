package org.prgrms.kdt;

import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@ComponentScan(basePackages = {"org.prgrms.kdt.order", "org.prgrms.kdt.voucher"})
@ComponentScan(basePackageClasses = {Order.class, Voucher.class}) // 스캔 범위 지정
@PropertySource("application.properties")
public class AppConfiguration {
}
