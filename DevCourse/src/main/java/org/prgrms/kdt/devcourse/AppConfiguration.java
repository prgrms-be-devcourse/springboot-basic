package org.prgrms.kdt.devcourse;

import org.prgrms.kdt.devcourse.order.Order;
import org.prgrms.kdt.devcourse.voucher.Voucher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {Order.class, Voucher.class})
public class AppConfiguration {



}
