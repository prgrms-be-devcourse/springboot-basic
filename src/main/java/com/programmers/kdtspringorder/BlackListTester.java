package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.user.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BlackListTester {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        UserService userService = applicationContext.getBean(UserService.class);

        System.out.println(userService.findByUserId("neilson").orElse(null));
        System.out.println(userService.findAll());
        System.out.println(userService.findByUserId("neilson").orElse(null));
        System.out.println(userService.findAll());


    }
}
