package com.prgrms.w3springboot;

import com.prgrms.w3springboot.io.CommandLine;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        applicationContext.getBean(CommandLine.class).run();

        applicationContext.close();
    }
}
