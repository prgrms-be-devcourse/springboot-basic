package com.prgrms.springbootbasic.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan(basePackages = {
        "com.prgrms.springbootbasic.app",
        "com.prgrms.springbootbasic.console",
        "com.prgrms.springbootbasic.voucher",
        "com.prgrms.springbootbasic.user"
})
@PropertySource(value = "application.yml")
public class AppConfiguration {
}
