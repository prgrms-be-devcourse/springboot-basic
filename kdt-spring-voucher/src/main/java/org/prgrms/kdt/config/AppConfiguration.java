package org.prgrms.kdt.config;

import org.prgrms.kdt.controller.VoucherController;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {
        "org.prgrms.kdt.domain",
        "org.prgrms.kdt.utill",
})
public class AppConfiguration {

}
