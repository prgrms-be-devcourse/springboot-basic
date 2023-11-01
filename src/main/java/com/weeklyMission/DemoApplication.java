package com.weeklyMission;

import com.weeklyMission.client.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class DemoApplication {
    private final ConfigurableApplicationContext applicationContext;

    public DemoApplication(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Profile("console")
    @Bean
    public void console(){
        Client client = applicationContext.getBean(Client.class);

        while(true){
            client.run();
        }
    }

    @Profile("web")
    @Bean
    public ServletWebServerFactory web(){
        return new TomcatServletWebServerFactory();
    }
}
