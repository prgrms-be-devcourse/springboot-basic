package org.programmers.kdtspringdemo.config;

import org.programmers.kdtspringdemo.voucher.model.Voucher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.programmers.kdtspringdemo.voucher")
public class AppConfiguration {

}
