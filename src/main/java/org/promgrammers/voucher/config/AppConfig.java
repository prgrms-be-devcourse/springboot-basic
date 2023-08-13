package org.promgrammers.voucher.config;


import org.promgrammers.voucher.view.Console;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class AppConfig {
    @Bean
    public Console console(){
        return new Console(new Scanner(System.in));
    }

}
