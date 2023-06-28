package com.devcourse.springbootbasic.application.repository.customer;

import com.devcourse.springbootbasic.application.constant.YamlProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
class CustomerRepositoryTest {

    @Autowired
    YamlProperties yamlProperties;

    @Test
    @DisplayName("진상고객 리스트 반환 테스트")
    void 진상고객리스트반환테스트() {
        var customerRepository = new CustomerRepository(yamlProperties);
        var list = customerRepository.findAllBlackCustomers();
        assertThat(list, notNullValue());
        assertThat(list, not(empty()));
    }

    @Configuration
    @ComponentScan(
            basePackages = {"com.devcourse.springbootbasic.application"}
    )
    static class Config {

    }

}