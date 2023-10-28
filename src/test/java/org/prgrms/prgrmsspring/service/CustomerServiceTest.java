package org.prgrms.prgrmsspring.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.repository.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.clear();
    }

    @DisplayName("고객을 추가할 수 있다.")
    @Test
    void createCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@email.com");

        //when
        Customer createdCustomer = service.create(customer);

        //then
        assertThat(createdCustomer).isEqualTo(customer);
    }

    @DisplayName("기존 고객을 수정할 수 있다.")
    @Test
    void updateCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@email.com");
        UUID customerId = customer.getCustomerId();
        service.create(customer);

        Customer updateCustomer = new Customer(customerId, "updateName", "update@email.com");

        //when
        Customer updatedCustomer = service.update(updateCustomer);

        //then
        assertThat(updatedCustomer).isEqualTo(updateCustomer);
        assertThat(updatedCustomer.getCustomerId()).isEqualTo(customerId);
        assertThat(updateCustomer.getEmail()).isNotEqualTo(customer.getEmail());
    }

    @DisplayName("존재하지 않는 고객인 경우 수정할 수 없다.")
    @Test
    void updateCustomer2() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@email.com");
        UUID customerId = customer.getCustomerId();

        Customer updateCustomer = new Customer(customerId, "updateName", "update@email.com");

        //when //then
        assertThatThrownBy(() -> service.update(updateCustomer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.NOT_FOUND_CUSTOMER.getMessage());
    }

    @DisplayName("기존 고객을 삭제할 수 있다.")
    @Test
    void deleteCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@email.com");
        UUID customerId = customer.getCustomerId();
        service.create(customer);

        //when
        service.delete(customerId);

        //then
        assertThat(service.findAll()).isNullOrEmpty();
    }


    @DisplayName("존재하지 않는 고객은 삭제할 수 없다.")
    @Test
    void deleteNotExistingCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@email.com");
        UUID customerId = customer.getCustomerId();

        //when //then
        assertThatThrownBy(() -> service.delete(customerId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.NOT_FOUND_CUSTOMER.getMessage());
    }


    @DisplayName("존재하는 모든 고객을 조회할 수 있다.")
    @Test
    void findAllCustomer() {
        //given
        Customer customer1 = new Customer(UUID.randomUUID(), "customerNameFirst", "customer1@email.com", true);
        Customer customer2 = new Customer(UUID.randomUUID(), "customerNameSecond", "customer2@email.com", false);
        Customer customer3 = new Customer(UUID.randomUUID(), "customerNameThird", "customer3@email.com", false);
        List<Customer> customers = List.of(customer1, customer2, customer3);
        customers.forEach(service::create);

        //when
        List<Customer> allCustomers = service.findAll();

        //then
        assertThat(allCustomers)
                .hasSize(customers.size())
                .containsOnlyOnceElementsOf(customers);
    }

    @DisplayName("저장소가 비어있다면, 모든 고객을 조회해도 빈 리스트를 반환한다.")
    @Test
    void findAllCustomerWhenEmpty() {
        //given //when
        List<Customer> allCustomers = service.findAll();
        //then
        assertThat(allCustomers).isEmpty();
    }

    @DisplayName("블랙 고객들을 조회할 수 있다.")
    @Test
    void findAllBlack() {
        //given
        Customer customer1 = new Customer(UUID.randomUUID(), "customerNameFirst", "customer1@email.com", true);
        Customer customer2 = new Customer(UUID.randomUUID(), "customerNameSecond", "customer2@email.com", true);
        Customer customer3 = new Customer(UUID.randomUUID(), "customerNameThird", "customer3@email.com", false);
        List<Customer> customers = List.of(customer1, customer2, customer3);
        List<Customer> blackCustomers = List.of(customer1, customer2);
        customers.forEach(service::create);

        //when
        List<Customer> blackAll = service.findBlackAll();

        //then
        assertThat(blackAll)
                .hasSize(blackCustomers.size())
                .containsOnlyOnceElementsOf(blackCustomers);
    }

    @DisplayName("블랙 고객들이 없다면, 빈 리스트를 반환한다.")
    @Test
    void findAllBlackWhenEmpty() {
        //given
        Customer notBlackCustomer1 = new Customer(UUID.randomUUID(), "customerName", "customer1@email.com", false);
        Customer notBlackCustomer2 = new Customer(UUID.randomUUID(), "customerNameSecond", "customer2@email.com", false);
        List<Customer> notBlackCustomers = List.of(notBlackCustomer1, notBlackCustomer2);
        notBlackCustomers.forEach(service::create);

        //when
        List<Customer> blackAll = service.findBlackAll();

        //then
        assertThat(blackAll).isEmpty();
    }
}