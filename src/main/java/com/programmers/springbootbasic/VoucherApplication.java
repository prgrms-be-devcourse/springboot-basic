package com.programmers.springbootbasic;

import com.programmers.springbootbasic.exception.AppExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class VoucherApplication {
    private final ConfigurableApplicationContext applicationContext;

    public VoucherApplication(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(
            VoucherApplication.class, args
        );
    }

    @Profile(value = "command")
    @Bean
    public void init() {
        startAppWithErrorHandler(applicationContext);
    }

    @Profile(value = "tomcat")
    @Bean
    public ServletWebServerFactory servletTomcatServerContainer() {
        return new TomcatServletWebServerFactory();
    }

    private static void startAppWithErrorHandler(
        ConfigurableApplicationContext applicationContext
    ) {
        AppExceptionHandler exceptionHandler = applicationContext.getBean(
            AppExceptionHandler.class);
        do {
            exceptionHandler.handle();
        } while (applicationContext.isActive());
    }
}
