package org.prgrms.assignment.voucher.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.assignment.voucher"}
)
public class VoucherAppConfiguration {
}
