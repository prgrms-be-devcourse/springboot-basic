package org.prgms.order;


import org.prgms.order.order.model.Order;
import org.prgms.order.voucher.model.Voucher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgms.order.*", "org.prgms.order.voucher.*"})
public class AppConfiguration {
}
