package org.programmers.kdt.weekly;

import org.programmers.kdt.weekly.command.StartCommandLine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class SpringVoucherApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(SpringVoucherApplication.class, args);
        var commandLineApplication = applicationContext.getBean(StartCommandLine.class);
        commandLineApplication.run();
    }
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}