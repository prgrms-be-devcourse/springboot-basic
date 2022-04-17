package org.prgrms.springbasic.config;

import org.prgrms.springbasic.SpringBasicApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = SpringBasicApplication.class)
public class AppConfig {
}
