package org.prgms;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.Files;

public class CustomerBlackList {
    public static void main(String[] args) throws IOException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var resource = applicationContext.getResource("file:customer_blacklist.csv");

        var strings = Files.readAllLines(resource.getFile().toPath());

        System.out.println(strings.stream().reduce("", (a, b) -> a + "\n" + b));
    }
}
