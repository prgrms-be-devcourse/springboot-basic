package org.prgrms.kdtspringhomework;

import org.prgrms.kdtspringhomework.config.AppConfiguration;
import org.prgrms.kdtspringhomework.io.OutputConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CustomerBlackListApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        OutputConsole outputConsole = new OutputConsole();


    }
}
