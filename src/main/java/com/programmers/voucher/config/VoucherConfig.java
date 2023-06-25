package com.programmers.voucher.config;

import com.programmers.voucher.view.ConsoleInput;
import com.programmers.voucher.view.ConsoleOutput;
import com.programmers.voucher.view.Input;
import com.programmers.voucher.view.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoucherConfig {
    //TODO profile=local 일 때 Console 적용되도록 개선
    @Bean
    public Input input() {
        return new ConsoleInput();
    }

    @Bean
    public Output output() {
        return new ConsoleOutput();
    }
}
