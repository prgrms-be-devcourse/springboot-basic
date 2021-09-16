package org.prgrms.kdtspringorder;

import org.prgrms.kdtspringorder.BlackCustomer.BlacklistCustomer;
import org.prgrms.kdtspringorder.BlackCustomer.BlacklistService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class BlacklistApplication {
    public static void main(String[] args) {
//        var springApplication = new SpringApplication(AppConfiguration.class);
//        springApplication.setAdditionalProfiles("production");
//        var applicationContext = springApplication.run(args);
//
//        BlacklistService blacklistService = applicationContext.getBean(BlacklistService.class);
//        List<BlacklistCustomer> blacklist = blacklistService.getBlacklist();

        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        var environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("local");
        applicationContext.refresh();


//        var environment = applicationContext.getEnvironment();
//        environment.setActiveProfiles("local");
//        applicationContext.refresh();

        BlacklistService blacklistService = applicationContext.getBean(BlacklistService.class);
        List<BlacklistCustomer> blacklist = blacklistService.getBlacklist();


        StringBuilder sb = new StringBuilder();
        sb.append("=== Blacklist ===");
        sb.append(System.lineSeparator());
        for (var customer : blacklist) {
            sb.append(customer);
            sb.append(System.lineSeparator());
        }
        System.out.println(sb);
    }
}
