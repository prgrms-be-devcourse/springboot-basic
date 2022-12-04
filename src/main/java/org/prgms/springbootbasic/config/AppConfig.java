package org.prgms.springbootbasic.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "org.prgms.springbootbasic.controller",
        "org.prgms.springbootbasic.service",
        "org.prgms.springbootbasic.repository",
})
public class AppConfig {
}
