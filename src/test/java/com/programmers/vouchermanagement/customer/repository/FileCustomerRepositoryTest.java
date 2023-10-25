package com.programmers.vouchermanagement.customer.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void cleanup() {
        customerRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("저장된 블랙리스트 csv파일을 성공적으로 읽고 로드한다")
    void testLoadingBlacklistFileOnInit() {
        assertThat(customerRepository, notNullValue());
        assertThat(appProperties.getCSVCustomerFilePath(), is("src/test/resources/blacklist-test.csv"));

        //when
        final List<Customer> blacklist = customerRepository.findBlackCustomers();

        //then
        assertThat(blacklist.isEmpty(), is(false));
    }

    @Test
    @DisplayName("블랙리스트에 저장된 고객이 없을 시 빈 리스트를 반환한다.")
    void testFindBlackCustomersSuccessful_ReturnEmptyList() {
        //given
        customerRepository.deleteAll();

        //when
        final List<Customer> blacklist = customerRepository.findBlackCustomers();

        //then
        assertThat(blacklist.isEmpty(), is(true));
    }

    @Test
    @DisplayName("저장된 고객이 없으면 빈 리스트를 반환한다.")
    void testFindCustomersSuccessful_ReturnEmptyList() {
        //when
        List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers.isEmpty(), is(true));
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
    @DisplayName("저장된 고객이 있으면 고객들의 리스트를 반환한다.")
    void testFindCustomersSuccessful_ReturnList() {
        //given
        Customer firstCustomer = new Customer(UUID.randomUUID(), "first-customer");
        Customer secondCustomer = new Customer(UUID.randomUUID(), "second-customer");
        customerRepository.save(firstCustomer);
        customerRepository.save(secondCustomer);

        //when
        List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers.isEmpty(), is(false));
        assertThat(customers, hasSize(greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("존재하지 않는 고객의 아이디 검색에 빈 Optional을 반환한다.")
    void testFindCustomerByIdFailed_ReturnEmptyOptional() {
        //given
        UUID randomId = UUID.randomUUID();

        //when
        Optional<Customer> foundCustomer = customerRepository.findById(randomId);

        //then
        assertThat(foundCustomer.isEmpty(), is(true));
    }

    @Test
    @DisplayName("아이디 검색으로 고객 조회를 성공한다.")
    void testFindCustomerByIdSuccessful() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer2");
        customerRepository.save(customer);

        //when
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getCustomerId());

        //then
        assertThat(foundCustomer.isEmpty(), is(false));
        assertThat(foundCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("저장된 고객의 정보 수정을 성공한다.")
    void testUpdateCustomerSuccessful() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");

        //when
        Customer updatedCustomer = new Customer(customer.getCustomerId(), "updated-test-customer");
        customerRepository.save(updatedCustomer);

        //then
        Customer founcCustomer = customerRepository.findById(customer.getCustomerId()).get();
        assertThat(founcCustomer.getName(), is(updatedCustomer.getName()));
    }

    @Test
    @DisplayName("저장된 고객의 정보를 삭제한다.")
    void testDeleteCustomerSuccessful() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test-customer");
        customerRepository.save(customer);

        //when
        customerRepository.deleteById(customer.getCustomerId());

        //then
        Optional<Customer> emptyIfDeleted = customerRepository.findById(customer.getCustomerId());
        assertThat(emptyIfDeleted.isEmpty(), is(true));
    }
}
