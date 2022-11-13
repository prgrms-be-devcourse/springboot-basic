package org.prgrms.springorder.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import org.prgrms.springorder.controller.Console;
import org.prgrms.springorder.controller.ConsoleInput;
import org.prgrms.springorder.controller.ConsoleOutput;
import org.prgrms.springorder.controller.Input;
import org.prgrms.springorder.controller.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Input consoleInput() {
        return new ConsoleInput(new BufferedReader(new InputStreamReader(System.in)));
    }

    @Bean
    public Output consoleOutput() {
        return new ConsoleOutput(new BufferedWriter(new OutputStreamWriter(System.out)));
    }

    @Bean
    public Console console() {
        return new Console(consoleInput(), consoleOutput());
    }

}
