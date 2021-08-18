package org.prgms.order;


import org.prgms.order.order.Order;
import org.prgms.order.voucher.Voucher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {Order.class, Voucher.class})
public class AppConfiguration {
}
