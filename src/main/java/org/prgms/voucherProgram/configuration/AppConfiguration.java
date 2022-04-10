package org.prgms.voucherProgram.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AppConfiguration {

    @Value("${file.path.blacklist}")
    private String blacklistFilePath;

    @Bean
    Resource blacklistResource(ApplicationContext applicationContext) {
        return applicationContext.getResource(blacklistFilePath);
    }
}
