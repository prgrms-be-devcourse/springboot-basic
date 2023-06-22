package prgms.spring_week1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import prgms.spring_week1.io.Console;
import prgms.spring_week1.io.Input;
import prgms.spring_week1.io.Output;


@Configuration
public class AppConfiguration {
    @Bean
    public Input input(){
        return new Console();
    }
    @Bean
    public Output output(){
        return new Console();
    }
}
