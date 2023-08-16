package com.prgrms.config;

import com.prgrms.common.util.Generator;
import com.prgrms.common.util.TestGeneratorImp;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
public class TestConfig {

    @Bean
    Generator generator() {
        return new TestGeneratorImp();
    }

}
