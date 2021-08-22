package com.prgrm.kdt;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.prgrm.kdt.order", "com.prgrm.kdt.voucher"})
//@ComponentScan(basePackageClasses = {Order.class, Voucher.class})
public class AppConfiguration {

}
