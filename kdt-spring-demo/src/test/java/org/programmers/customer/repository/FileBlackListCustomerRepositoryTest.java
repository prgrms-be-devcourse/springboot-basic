package org.programmers.customer.repository;

import org.junit.jupiter.api.*;
import org.programmers.customer.model.BlackListCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileBlackListCustomerRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(FileBlackListCustomerRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.customer.repository"})
    static class Config {
        @Bean
        BlackListCustomerRepository BlackListCustomerRepository() {
            return new FileBlackListCustomerRepository();
        }
    }

    @Autowired
    FileBlackListCustomerRepository fileBlackListCustomerRepository;

    BlackListCustomer blackListCustomer;

    @BeforeAll
    void setUp() {
        fileBlackListCustomerRepository.loadBlackList();
        blackListCustomer = new BlackListCustomer(UUID.randomUUID(), "test1");
    }


    @Test
    @DisplayName("모든 블랙리스트 회원을 조회할 수 있다.")
    void findAllBlackList() {
        List<BlackListCustomer> allBlackList = fileBlackListCustomerRepository.findAllBlackList();
        assertThat(allBlackList, hasSize(5));
    }

    @Test
    @DisplayName("블랙리스트 회원을 추가할 수 있다.")
    void save() {
        fileBlackListCustomerRepository.save(blackListCustomer);

        List<BlackListCustomer> allBlackList = fileBlackListCustomerRepository.findAllBlackList();
        assertThat(allBlackList, hasSize(6));
    }
}