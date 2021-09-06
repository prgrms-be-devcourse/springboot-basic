package com.prgrms.w3springboot;

import com.prgrms.w3springboot.configuration.AppConfig;
import com.prgrms.w3springboot.customer.BlacklistCustomer;
import com.prgrms.w3springboot.customer.service.BlacklistService;
import org.springframework.boot.SpringApplication;

import java.util.List;

public class BlacklistTestApplication {
    public static void main(String[] args) {
        var springApplication = new SpringApplication(AppConfig.class);
        springApplication.setAdditionalProfiles("production");
        var applicationContext = springApplication.run(args);

        BlacklistService blacklistService = applicationContext.getBean(BlacklistService.class);
        List<BlacklistCustomer> blacklist = blacklistService.getBlacklist();
        printBlacklist(blacklist);

        applicationContext.close();
    }

    private static void printBlacklist(final List<BlacklistCustomer> blacklist) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Blacklist Test Program ===");
        sb.append(System.lineSeparator());
        for (var customer : blacklist) {
            sb.append(customer);
            sb.append(System.lineSeparator());
        }
        System.out.println(sb);
    }
}