package org.prgrms.dev;

import org.prgrms.dev.config.AppConfiguration;
import org.prgrms.dev.blacklist.service.BlacklistService;
import org.prgrms.dev.io.OutputConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BlacklistApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        BlacklistService blacklistService = applicationContext.getBean(BlacklistService.class);
        OutputConsole outputConsole = new OutputConsole();
        outputConsole.printBlackList(blacklistService.blackList());
    }
}