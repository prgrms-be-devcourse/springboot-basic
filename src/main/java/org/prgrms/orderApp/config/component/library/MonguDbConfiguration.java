package org.prgrms.orderApp.config.component.library;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.orderApp.infrastructure.library.monguDb"})
public class MonguDbConfiguration {
}
