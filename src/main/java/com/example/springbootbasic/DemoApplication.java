package com.example.springbootbasic;

import com.example.springbootbasic.config.CsvProperties;
import com.example.springbootbasic.config.JdbcProperties;
import com.example.springbootbasic.console.Console;
import com.example.springbootbasic.console.ConsoleStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import static com.example.springbootbasic.console.ConsoleStatus.CONTINUE;

@SpringBootApplication
@EnableConfigurationProperties(value = {JdbcProperties.class, CsvProperties.class})
public class DemoApplication {
    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(DemoApplication.class, args);
        Console console = ac.getBean(Console.class);

        ConsoleStatus status = CONTINUE;
        while (status == CONTINUE) {
            status = console.process();
        }
    }
}
