package org.programmers.customer.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.programmers.customer.model.BlackListCustomer;
import org.programmers.customer.repository.FileBlackListCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlackListCustomerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BlackListCustomerServiceTest.class);

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.customer.repository"})
    static class Config {
        @Bean
        BlackListCustomerService blackListCustomerService(FileBlackListCustomerRepository fileBlackListCustomerRepository) {
            return new BlackListCustomerService(fileBlackListCustomerRepository);
        }
    }

    @Autowired
    BlackListCustomerService blackListCustomerService;

    @Test
    void getBlackList() throws IOException {
        List<BlackListCustomer> blackList = blackListCustomerService.getBlackList();
        assertThat(blackList, hasSize(5));
    }
}