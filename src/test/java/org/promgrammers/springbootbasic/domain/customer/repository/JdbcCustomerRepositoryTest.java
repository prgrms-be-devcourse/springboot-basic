package org.promgrammers.springbootbasic.domain.customer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.controller.CommandLineController;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.JdbcCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yaml")
@ActiveProfiles("jdbc")
class JdbcCustomerRepositoryTest {

    @MockBean
    private CommandLineController controller;

    @Autowired
    JdbcCustomerRepository customerRepository;

    @BeforeEach
    void init() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 성공 - 고객 저장")
    void insertSuccessTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();
        String username = "A";
        Customer customer = new Customer(customerId, username);
        //when
        Customer savedCustomer = assertDoesNotThrow(() -> customerRepository.save(customer));

        //then
        assertNotNull(savedCustomer);
        assertThat(savedCustomer.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(savedCustomer.getCustomerType()).isEqualTo(customer.getCustomerType());
        assertThat(savedCustomer.getUsername()).isEqualTo(customer.getUsername());
    }

    @Test
    @DisplayName("저장 실패 - 중복 고객 저장")
    void insertDuplicateCustomerTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();
        String username = "A";
        Customer customer = new Customer(customerId, username);

        //when
        assertDoesNotThrow(() -> customerRepository.save(customer));

        //then
        assertThrows(DataAccessException.class, () -> customerRepository.save(customer));
    }

    @Test
    @DisplayName("단건 조회 성공 - 고객ID로 조회")
    void findByIdSuccessTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();
        String username = "A";
        Customer customer = new Customer(customerId, username);
        Customer savedCustomer = assertDoesNotThrow(() -> customerRepository.save(customer));

        //when
        Optional<Customer> repositoryById = assertDoesNotThrow(() -> customerRepository.findById(savedCustomer.getCustomerId()));
        Customer foundCustomer = repositoryById.get();

        //then
        assertThat(repositoryById.isPresent()).isEqualTo(true);
        assertThat(foundCustomer.getCustomerId()).isEqualTo(savedCustomer.getCustomerId());
        assertThat(foundCustomer.getCustomerType()).isEqualTo(savedCustomer.getCustomerType());
        assertThat(foundCustomer.getUsername()).isEqualTo(savedCustomer.getUsername());
    }

    @Test
    @DisplayName("단건 조회 실패 - 존재하지 않는 고객 조회")
    void findByIdNonexistentVoucherTest() throws Exception {

        // given
        UUID nonExistentCustomerId = UUID.randomUUID();

        // when
        Optional<Customer> repositoryById = assertDoesNotThrow(() -> customerRepository.findById(nonExistentCustomerId));

        // then
        assertThat(repositoryById.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("단건 조회 성공 - 고객 Username으로 조회")
    void findByUsernameSuccessTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();
        String username = "A";
        Customer customer = new Customer(customerId, username);
        Customer savedCustomer = assertDoesNotThrow(() -> customerRepository.save(customer));

        //when
        Optional<Customer> repositoryById = assertDoesNotThrow(() -> customerRepository.findByUsername(savedCustomer.getUsername()));
        Customer foundCustomer = repositoryById.get();

        //then
        assertThat(repositoryById.isPresent()).isEqualTo(true);
        assertThat(foundCustomer.getCustomerId()).isEqualTo(savedCustomer.getCustomerId());
        assertThat(foundCustomer.getCustomerType()).isEqualTo(savedCustomer.getCustomerType());
        assertThat(foundCustomer.getUsername()).isEqualTo(savedCustomer.getUsername());
    }

    @Test
    @DisplayName("단건 조회 실패 - 존재하지 않는 고객 Username")
    void findByUsernameNonexistentVoucherTest() throws Exception {

        // given
        String nonExistentCustomerUsername = "AAA";

        // when
        Optional<Customer> repositoryById = assertDoesNotThrow(() -> customerRepository.findByUsername(nonExistentCustomerUsername));

        // then
        assertThat(repositoryById.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("전체 조회 성공 - 고객 전체 조회")
    void findAllSuccessTest() throws Exception {

        //given
        int saveCount = 5;
        for (int i = 1; i <= saveCount; i++) {
            Customer customer;
            customer = new Customer(UUID.randomUUID(), ("A" + i));
            customerRepository.save(customer);
        }

        //when
        List<Customer> customerList = assertDoesNotThrow(() -> customerRepository.findAll());

        //then
        assertThat(customerList.isEmpty()).isEqualTo(false);
        assertThat(customerList.size()).isEqualTo(saveCount);
    }

    @Test
    @DisplayName("업데이트 성공 - 고객 업데이트")
    void updateSuccessTest() throws Exception {

        //given
        UUID customerId = UUID.randomUUID();
        String username = "A";
        Customer customer = new Customer(customerId, username);
        Customer savedCustomer = assertDoesNotThrow(() -> customerRepository.save(customer));

        //when
        String updateUsername = "B";
        Customer updateCustomer = new Customer(savedCustomer.getCustomerId(), updateUsername);
        assertDoesNotThrow(() -> customerRepository.update(updateCustomer));
        Optional<Customer> updatedCustomerById = assertDoesNotThrow(() -> customerRepository.findById(updateCustomer.getCustomerId()));

        //then
        assertThat(updatedCustomerById.isPresent()).isEqualTo(true);
        assertThat(updatedCustomerById.get().getUsername()).isEqualTo(updateUsername);
        assertThat(updatedCustomerById.get().getCustomerId()).isEqualTo(savedCustomer.getCustomerId());
    }

    @Test
    @DisplayName("전체 삭제 - 바우처 전체 삭제")
    void deleteAllSuccessTest() throws Exception {

        //given
        int saveCount = 5;
        for (int i = 1; i <= saveCount; i++) {
            Customer customer;
            customer = new Customer(UUID.randomUUID(), ("A" + i));
            customerRepository.save(customer);
        }

        //when
        assertDoesNotThrow(() -> customerRepository.deleteAll());
        List<Customer> customerList = assertDoesNotThrow(() -> customerRepository.findAll());

        //then
        assertThat(customerList.size()).isEqualTo(0);
        assertThat(customerList.isEmpty()).isEqualTo(true);
    }
}