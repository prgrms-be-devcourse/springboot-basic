package me.programmers.springboot.basic.springbootbasic.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
        "me.programmers.springboot.basic.springbootbasic.voucher",
        "me.programmers.springboot.basic.springbootbasic.customer"})
@Import(JdbcConfig.class)
public class AppConfig {

}
