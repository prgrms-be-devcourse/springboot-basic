package org.prgrms.kdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class kdtApplication{
    public static void main(String[] args) {
        SpringApplication.run(kdtApplication.class, args);
    }

    // for delete, put method
    @Configuration
    public class MvcConfig extends WebMvcConfigurationSupport {

        @Bean
        public HiddenHttpMethodFilter httpMethodFilter() {
            HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
            return hiddenHttpMethodFilter;
        }

    }
}
