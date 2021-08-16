package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.order.Order;
import com.programmers.kdtspringorder.voucher.VoucherService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan (basePackageClasses = {Order.class, VoucherService.class})
public class AppConfiguration {

}
