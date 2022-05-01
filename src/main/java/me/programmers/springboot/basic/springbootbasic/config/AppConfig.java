package me.programmers.springboot.basic.springbootbasic.config;

import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import me.programmers.springboot.basic.springbootbasic.io.In;
import me.programmers.springboot.basic.springbootbasic.io.Out;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
        "me.programmers.springboot.basic.springbootbasic.voucher",
        "me.programmers.springboot.basic.springbootbasic.customer",
        "me.programmers.springboot.basic.springbootbasic.command"
})
@Import(JdbcConfig.class)
public class AppConfig {
    @Bean
    public ConsoleInput consoleInput() {
        return new In();
    }

    @Bean
    public ConsoleOutput consoleOutput() {
        return new Out();
    }
}
