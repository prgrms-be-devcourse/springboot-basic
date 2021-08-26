package org.prgrms.orderApp;

import org.prgrms.orderApp.presentation.commandOperator.CommandOperator;
import org.prgrms.orderApp.presentation.commandOperator.VoucherCommandOperator;
import org.prgrms.orderApp.config.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfiguration.class);
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        CommandOperator commandOperator = applicationContext.getBean(VoucherCommandOperator.class);
        // app start
        ((VoucherCommandOperator) commandOperator).CMDAppStart();
    }



}
