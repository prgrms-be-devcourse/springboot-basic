package com.prgms.vouchermanager;

import com.prgms.vouchermanager.contorller.front.FrontController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class VouchermanagerApplication {

    private static ApplicationContext ac;


    public VouchermanagerApplication(ConfigurableApplicationContext applicationContext) {
        this.ac = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(VouchermanagerApplication.class, args);
    }

    @Profile(value = "tomcat")
    @Bean
    public ServletWebServerFactory servletTomcatServerContainer() {
        return new TomcatServletWebServerFactory();
    }

    @Profile("console")
    @Bean
    public void init() {
        FrontController frontController = ac.getBean(FrontController.class);
        frontController.run();
    }

}
