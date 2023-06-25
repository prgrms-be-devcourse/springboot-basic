package com.programmers.voucher;

import com.programmers.voucher.io.Console;
import com.programmers.voucher.io.TextIoConsole;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Console console() {
        TextIO textIO = TextIoFactory.getTextIO();
        return new TextIoConsole(textIO);
    }
}
