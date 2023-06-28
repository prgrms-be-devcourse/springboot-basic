package com.example.demo.common;

import com.example.demo.common.io.impl.ConsoleInput;
import com.example.demo.common.io.impl.ConsoleOutput;
import com.example.demo.common.io.Input;
import com.example.demo.common.io.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.demo.voucher")
public class AppConfiguration {

    @Bean
    public Input consoleInput() {
        return new ConsoleInput();
    }

    @Bean
    public Output consoleOutput() {
        return new ConsoleOutput();
    }

}
