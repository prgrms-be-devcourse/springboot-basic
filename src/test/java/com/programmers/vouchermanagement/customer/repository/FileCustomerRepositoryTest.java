package com.programmers.vouchermanagement.customer.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.programmers.vouchermanagement.configuration.TestConfig;
import com.programmers.vouchermanagement.configuration.properties.AppProperties;
import com.programmers.vouchermanagement.customer.domain.Customer;

@SpringJUnitConfig(TestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class FileCustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AppProperties appProperties;

    @Test
    @Order(1)
    @DisplayName("저장된 블랙리스트 csv파일을 성공적으로 읽고 로드한다")
    void testLoadingBlacklistFileOnInit() {
        assertThat(customerRepository, notNullValue());
        assertThat(appProperties.getCustomerFilePath(), is("src/test/resources/blacklist-test.csv"));
    }
}
