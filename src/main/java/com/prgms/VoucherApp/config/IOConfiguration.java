package com.prgms.VoucherApp.config;

import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import com.prgms.VoucherApp.view.console.ConsoleInputView;
import com.prgms.VoucherApp.view.console.ConsoleOutputView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IOConfiguration {

    @Bean
    public Input input() {
        return new ConsoleInputView();
    }

    @Bean
    public Output output() {
        return new ConsoleOutputView();
    }
}
