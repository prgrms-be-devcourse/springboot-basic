package org.promgrammers.springbootbasic.config;

import org.promgrammers.springbootbasic.view.Console;
import org.promgrammers.springbootbasic.view.Input;
import org.promgrammers.springbootbasic.view.Output;
import org.promgrammers.springbootbasic.view.Reader;
import org.promgrammers.springbootbasic.view.Writer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public Console console(Input input, Output output) {
        return new Console(input, output);
    }

    @Bean
    public Input input() {
        return new Reader(new Scanner(System.in));
    }

    @Bean
    public Output output() {
        return new Writer();
    }
}