package me.programmers.springboot.basic.springbootbasic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ApplicationController {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    private static final String csvFile = "file:customer_blacklist.csv";

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
            Resource resource = context.getResource(csvFile);
            List<String> blackList = Files.readAllLines(resource.getFile().toPath());
            System.out.println("customer blacklist " + blackList.stream().reduce("", (a, b) -> a + "\n" + b));
        } catch (IOException e) {
            logger.error("해당 파일을 찾을 수 없습니다. " + csvFile);
        }

    }
}
