package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandLineApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandAppTest {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        applicationContext.getBean(CommandLineApplication.class).run();
    }
}