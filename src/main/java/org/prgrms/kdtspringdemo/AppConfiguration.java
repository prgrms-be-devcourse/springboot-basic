package org.prgrms.kdtspringdemo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdtspringdemo.order", "org.prgrms.kdtspringdemo.voucher"})
public class AppConfiguration {
}
