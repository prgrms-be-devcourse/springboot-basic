package org.prgms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MainApplication implements WebMvcConfigurer {
    public static void main(String[] args) throws Exception {
        var context = SpringApplication.run(MainApplication.class, args);
        context.getBean(CommandLineApplication.class).run();
    }
}
