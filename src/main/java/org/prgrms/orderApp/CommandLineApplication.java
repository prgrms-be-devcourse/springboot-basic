package org.prgrms.orderApp;

import org.prgrms.orderApp.config.component.library.ConsoleConfiguration;
import org.prgrms.orderApp.config.component.library.MonguDbConfiguration;
import org.prgrms.orderApp.config.component.presentation.CommandOperatorConfiguration;
import org.prgrms.orderApp.config.component.AppConfiguration;
import org.prgrms.orderApp.presentation.commandOperator.CommandOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    private final static Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);


    public static void main(String[] args) {
        logger.error("test");
        logger.info("info");
        var context = new AnnotationConfigApplicationContext();
        context.register(AppConfiguration.class);
        context.register(ConsoleConfiguration.class);
        context.register(MonguDbConfiguration.class);
        context.register(CommandOperatorConfiguration.class);

        var environment = context.getEnvironment();
        environment.setActiveProfiles("prod");
        context.refresh();
        CommandOperator commandOperator = context.getBean(CommandOperator.class);
        // app start
        //((VoucherCommandOperator) commandOperator).CMDAppStart();
    }



}
