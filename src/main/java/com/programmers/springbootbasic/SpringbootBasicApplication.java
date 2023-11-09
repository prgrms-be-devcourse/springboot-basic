package com.programmers.springbootbasic;

import com.programmers.springbootbasic.common.handler.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringbootBasicApplication {
    private final ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBasicApplication.class, args);
    }

    @Profile("default")
    @Bean
    public ServletWebServerFactory servletTomcatServerContainer() {
        return new TomcatServletWebServerFactory();
    }

    @Profile("command")
    @Bean
    public void init() {
        CommandHandler commandHandler = applicationContext.getBean(CommandHandler.class);
        commandHandler.run();
        System.exit(SpringApplication.exit(applicationContext, () -> 0));
    }

}
