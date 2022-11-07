package org.prgrms.springorder.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import org.prgrms.springorder.controller.ConsoleInput;
import org.prgrms.springorder.controller.ConsoleOutput;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ConsoleInput consoleInput() {
        return new ConsoleInput(new BufferedReader(new InputStreamReader(System.in)));
    }

    @Bean
    public ConsoleOutput consoleOutput() {
        return new ConsoleOutput(new BufferedWriter(new OutputStreamWriter(System.out)));
    }

}
