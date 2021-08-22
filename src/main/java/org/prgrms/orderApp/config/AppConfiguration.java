package org.prgrms.orderApp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.orderApp.model",
        "org.prgrms.orderApp.repository",
        "org.prgrms.orderApp.service",
        "org.prgrms.orderApp.CMDApplication",
        "org.prgrms.orderApp.monguDb"})
public class AppConfiguration {




}
