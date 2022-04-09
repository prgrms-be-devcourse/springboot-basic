package org.prgrms.vouchermanager.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
@ComponentScan(basePackages = "org.prgrms.vouchermanager")
@EnableConfigurationProperties
public class AppConfiguration {

    @Bean
    InputStream inputStream(){
        return System.in;
    }

    @Bean
    PrintStream printStream(){
        return System.out;
    }

}
