package org.prgrms.kdt;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineAppTest {
    public static void main(final String[] args) {
        final var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        applicationContext.getBean(CommandLineApplication.class).run();
        applicationContext.close();
    }
}
