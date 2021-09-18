package com.prgrms.w3springboot;

import com.prgrms.w3springboot.configuration.AppConfig;
import com.prgrms.w3springboot.io.CommandLine;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfig.class);
        var environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("production");
        applicationContext.refresh();

        applicationContext.getBean(CommandLine.class).run();

        applicationContext.close();
    }
}
