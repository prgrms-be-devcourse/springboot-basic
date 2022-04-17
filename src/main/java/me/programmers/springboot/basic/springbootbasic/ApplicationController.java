package me.programmers.springboot.basic.springbootbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

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

    public void showBlackList() {
        try {
            Resource resource = context.getResource("file:customer_blacklist.csv");
            List<String> blackList = Files.readAllLines(resource.getFile().toPath());
            System.out.println("customer blacklist " + blackList.stream().reduce("", (a, b) -> a + "\n" + b));
        } catch (IOException e) {

        }

    }
}
