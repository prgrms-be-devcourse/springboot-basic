package org.prgrms.kdt.config;

import org.prgrms.kdt.forward.io.ConsoleInput;
import org.prgrms.kdt.forward.io.ConsoleOutput;
import org.prgrms.kdt.forward.io.Input;
import org.prgrms.kdt.forward.io.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

@Configuration
public class AppConfig {

    @Bean
    public Input input() {
        return new ConsoleInput(new BufferedReader(new InputStreamReader(System.in)));
    }

    @Bean
    public Output output() {
        return new ConsoleOutput(new BufferedWriter(new OutputStreamWriter(System.out)));
    }
}
