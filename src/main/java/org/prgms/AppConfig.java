package org.prgms;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.prgms.repository", "org.prgms.service"})
public class AppConfig {
}
