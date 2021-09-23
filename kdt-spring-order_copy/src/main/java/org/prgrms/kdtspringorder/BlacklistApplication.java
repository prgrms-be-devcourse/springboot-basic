package org.prgrms.kdtspringorder;

import org.prgrms.kdtspringorder.BlackCustomer.BlacklistCustomer;
import org.prgrms.kdtspringorder.BlackCustomer.BlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class BlacklistApplication {
    private static final Logger logger = LoggerFactory.getLogger(BlacklistApplication.class);

    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        var environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("local");
        applicationContext.refresh();

        BlacklistService blacklistService = applicationContext.getBean(BlacklistService.class);
        try {
            List<BlacklistCustomer> blacklist = blacklistService.getBlacklist();

            StringBuilder sb = new StringBuilder();
            sb.append("=== Blacklist ===");
            sb.append(System.lineSeparator());
            for (var customer : blacklist) {
                sb.append(customer);
                sb.append(System.lineSeparator());
            }
            System.out.println(sb);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }
}
