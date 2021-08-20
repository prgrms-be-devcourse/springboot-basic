package org.prgrms.kdtspringdemo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdtspringdemo.order", "org.prgrms.kdtspringdemo.voucher", "org.prgrms.kdtspringdemo.configuration"})
@PropertySource("application.properties")
public class AppConfiguration {
}