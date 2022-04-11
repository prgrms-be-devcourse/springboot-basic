package com.programmers.springbootbasic.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.programmers.springbootbasic.configuration",
                                "com.programmers.springbootbasic.repository",
                                "com.programmers.springbootbasic.service",
                                "com.programmers.springbootbasic.domain"})
public class VoucherAppConfiguration {

}
