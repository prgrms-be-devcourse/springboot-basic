package me.programmers.springboot.basic.springbootbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class ApplicationController {

    private final ConfigurableApplicationContext context;

    public ApplicationController(SpringApplication springApplication, String[] args) {
        springApplication.setAdditionalProfiles("default");
        context = springApplication.run(args);
    }

    public void runCommandLineApp() {
        CommandLineApplication commandLineApplication = context.getBean(CommandLineApplication.class);
        commandLineApplication.run();
    }

}
