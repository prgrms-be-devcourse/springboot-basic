package org.prgrms.kdt;

import org.prgrms.kdt.configuration.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class BlackListTester {
    public static void main(String[] args) throws IOException {
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        var environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("prod");
        applicationContext.refresh();
        var blacklistResource = applicationContext.getResource("file:src/main/resources/customer_blacklist.csv");
        var blacklistFile = blacklistResource.getFile();
        var blacklist = Files.readAllLines(blacklistFile.toPath());
        System.out.println(blacklist.stream().reduce("", (a, b) -> a + "\n" + b));
    }
}
