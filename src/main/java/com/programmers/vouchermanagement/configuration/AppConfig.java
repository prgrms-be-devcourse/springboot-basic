package com.programmers.vouchermanagement.configuration;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.mock.MockTextTerminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {
    @Bean
    @Profile("!test")
    public TextIO textIO() {
        return TextIoFactory.getTextIO();
    }

    @Bean
    @Profile("test")
    public MockTextTerminal textTerminal() {
        return new MockTextTerminal();
    }

    @Bean
    @Profile("test")
    public TextIO mockTextIo(MockTextTerminal textTerminal) {
        return new TextIO(textTerminal);
    }
}
