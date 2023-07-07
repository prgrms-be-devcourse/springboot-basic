package com.programmers.springmission.global.config;

import com.programmers.springmission.view.Console;
import com.programmers.springmission.view.Input;
import com.programmers.springmission.view.InputReader;
import com.programmers.springmission.view.Output;
import com.programmers.springmission.view.OutputWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Console console(Input input, Output output) {
        return new Console(input, output);
    }

    @Bean
    public Input input() {
        return new InputReader();
    }

    @Bean
    public Output output() {
        return new OutputWriter();
    }
}

