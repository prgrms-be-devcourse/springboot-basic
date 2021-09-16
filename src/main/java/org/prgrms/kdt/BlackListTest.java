package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandLineApplication;
import org.prgrms.kdt.user.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.MessageFormat;

public class BlackListTest {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        // var userService = applicationContext.getBean(UserService.class);
        // userService.getBlackList().forEach(item -> System.out.println(item.toString()));
    }
}
