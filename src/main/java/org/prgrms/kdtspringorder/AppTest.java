package org.prgrms.kdtspringorder;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class AppTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfigurationClass.class);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("dev");
        applicationContext.refresh();
        App app = applicationContext.getBean(App.class);
        app.run();
    }
}
