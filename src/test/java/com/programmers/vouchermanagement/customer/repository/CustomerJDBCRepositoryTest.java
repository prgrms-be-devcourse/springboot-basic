package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.TestConfig;
import com.programmers.vouchermanagement.customer.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
class CustomerJDBCRepositoryTest {
    @Autowired
    CustomerJDBCRepository customerJDBCRepository;

    @Test
    @DisplayName("🆗 블랙리스트를 조회할 수 있다. 단, 블랙 고객이 없는 경우 빈 list가 반환된다.")
    void findAllBlackCustomerSucceed() {
        customerJDBCRepository.save(new Customer(UUID.randomUUID(), "고객1", false));
        customerJDBCRepository.save(new Customer(UUID.randomUUID(), "고객2", true));
        customerJDBCRepository.save(new Customer(UUID.randomUUID(), "고객3", false));
        List<Customer> customers = customerJDBCRepository.findAllBlackCustomer();
        assertThat(customers.isEmpty()).isFalse();
        assertThat(customers.stream().filter(customer -> !customer.isBlack()).toList()).isEmpty();
    }

    @Test
    @DisplayName("🆗 고객 정보를 저장할 수 있다.")
    void save() {
        customerJDBCRepository.save(new Customer(UUID.randomUUID(), "고객4"));
    }
}