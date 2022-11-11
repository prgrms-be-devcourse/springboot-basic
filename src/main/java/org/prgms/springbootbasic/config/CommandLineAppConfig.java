package org.prgms.springbootbasic.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "org.prgms.springbootbasic.app",
        "org.prgms.springbootbasic.service",
        "org.prgms.springbootbasic.repository",
        "org.prgms.springbootbasic.cli",
})
public class CommandLineAppConfig {
}
