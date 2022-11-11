package com.programmers.VoucherManagementApplication.config;

import com.programmers.VoucherManagementApplication.io.Console;
import com.programmers.VoucherManagementApplication.io.Input;
import com.programmers.VoucherManagementApplication.io.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Input input() {
        return new Input();
    }

    @Bean
    public Output output() {
        return new Output();
    }

    @Bean
    public Console console() {
        return new Console(input(), output());
    }
}
