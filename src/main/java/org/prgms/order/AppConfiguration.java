package org.prgms.order;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.prgms.order.*", "org.prgms.order.voucher.*"})
public class AppConfiguration {
}
