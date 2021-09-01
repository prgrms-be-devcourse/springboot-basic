package com.prgrms.w3springboot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {"com.prgrms.w3springboot.io", "com.prgrms.w3springboot.voucher", "com.prgrms.w3springboot.order"}
)
public class AppConfig {

}
