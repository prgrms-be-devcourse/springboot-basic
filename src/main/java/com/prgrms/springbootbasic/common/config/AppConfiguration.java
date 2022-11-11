package com.prgrms.springbootbasic.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.prgrms.springbootbasic.app",
        "com.prgrms.springbootbasic.console",
        "com.prgrms.springbootbasic.voucher"
})
public class AppConfiguration {
}
