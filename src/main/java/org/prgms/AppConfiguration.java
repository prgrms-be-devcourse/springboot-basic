package org.prgms;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan
public class AppConfiguration {
    @Bean
    public Io io(){
        return new Io();
    }


}
