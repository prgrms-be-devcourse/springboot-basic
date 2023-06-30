package org.prgrms.kdt.voucher.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.kdt.voucher"}
)
public class VoucherAppConfiguration {
}
