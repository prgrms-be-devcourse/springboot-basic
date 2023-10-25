package com.programmers.vouchermanagement.customer.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.List;
import java.util.UUID;

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

    @Test
    @DisplayName("고객 저장을 성공한다.")
    void testCustomerCreationSuccessful() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-user");

        //when
        Customer createdCustomer = customerRepository.save(customer);

        //then
        assertThat(createdCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("저장된 고객이 없으면 빈 리스트를 반환한다.")
    void testFindCustomersSuccessful_ReturnEmptyList() {
        //given
        customerRepository.deleteAll();

        //when
        List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers.isEmpty(), is(true));
    }

    @Test
    @Order(2)
    @DisplayName("저장된 블랙리스트에 있는 고객들의 리스트를 반환한다")
    void testFindBlackCustomersSuccessful_ReturnList() {
        //when
        final List<Customer> blacklist = customerRepository.findBlackCustomers();

        //then
        assertThat(blacklist.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("블랙리스트에 저장된 고객이 없을 시 빈 리스트를 반환한다.")
    void testFindBlackCustomersSuccessful_ReturnEmptyList() {
        //given
        customerRepository.deleteAll();

        //when
        final List<Customer> blacklist = customerRepository.findBlackCustomers();

        //then
        assertThat(blacklist.isEmpty(), is(true));
    }
}
