package org.prgrms.kdtspringhomework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.kdtspringhomework.order", "org.prgrms.kdtspringhomework.voucher", "org.prgrms.kdtspringhomework.config" }
)
public class AppConfiguration {
}