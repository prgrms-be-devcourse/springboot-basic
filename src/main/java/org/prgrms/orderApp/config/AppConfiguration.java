package org.prgrms.orderApp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.orderApp.application",
        "org.prgrms.orderApp.domain",
        "org.prgrms.orderApp.infrastructure",
        "org.prgrms.orderApp.presentation"
       })
@PropertySource("application.properties")
public class AppConfiguration {




}
