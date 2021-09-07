package com.prgrms.w3springboot;

import com.prgrms.w3springboot.io.CommandLine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class W3SpringbootApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(W3SpringbootApplication.class, args);
        applicationContext.getBean(CommandLine.class).run();
    }

}
