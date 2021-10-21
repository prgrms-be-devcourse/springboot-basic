package org.prgrms.kdt;

import org.prgrms.kdt.command.domain.CommandLineApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class CommandLineApplicationContext {
    public static void main(final String[] args) {
        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("dev");
        applicationContext.refresh();

        applicationContext.getBean(CommandLineApplication.class).run();
        applicationContext.close();
    }
}
