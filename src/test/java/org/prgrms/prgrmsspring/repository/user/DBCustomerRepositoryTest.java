package org.prgrms.prgrmsspring.repository.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class DBCustomerRepositoryTest {

    @Autowired
    private DBCustomerRepository repository;

    @AfterEach
    void tearDown() {
        repository.clear();
    }

    @DisplayName("저장소 내 모든 인원을 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        List<Customer> customers = createStubCustomers();
        customers.forEach(repository::insert);

        //when
        List<Customer> targetCustomerList = repository.findAll();

        //then
        assertThat(targetCustomerList).hasSize(3)
                .containsOnlyOnceElementsOf(customers);
    }

    @DisplayName("블랙 처리된 인원들을 조회할 수 있다.")
    @Test
    void findBlackAll() {
        //given
        List<Customer> customers = createStubCustomers();
        List<Customer> blackCustomers = createStubBlackCustomers();
        customers.forEach(repository::insert);

        //when
        List<Customer> targetCustomerList = repository.findBlackAll();

        //then
        assertThat(targetCustomerList).hasSize(2)
                .containsOnlyOnceElementsOf(blackCustomers);
    }

    @DisplayName("유효한 인원 아이디를 통해 인원을 조회할 수 있다.")
    @Test
    void findByValidCustomerId() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testName", "test@naver.com", true);
        repository.insert(customer);
        UUID validCustomerId = customer.getCustomerId();

        //when
        Optional<Customer> optionalCustomer = repository.findById(validCustomerId);

        //then
        assertThat(optionalCustomer).isPresent()
                .contains(customer);
    }

    @DisplayName("유효하지 않은 인원 아이디를 통해서는 인원을 조회할 수 없다.")
    @Test
    void findByInValidCustomerId() {
        //given
        UUID invalidCustomerId = UUID.randomUUID();

        //when
        Optional<Customer> optionalCustomer = repository.findById(invalidCustomerId);

        //then
        assertThat(optionalCustomer).isEmpty();
    }

    @DisplayName("고객을 추가할 수 있다.")
    @Test
    void insertCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testName", "test@naver.com", false);

        //when
        Customer insertCustomer = repository.insert(customer);

        //then
        assertThat(insertCustomer).isEqualTo(customer);
    }

    @DisplayName("고객의 이름은 영어 또는 한글로 구성되어야 한다.")
    @Test
    void customerNameContainsEnglishOrKorean() {
        //given
        String email = "test@email.com";

        String koreanName = "김나박이";
        String englishName = "kimNaParkLee";
        String mixedName = "김나Park이";
        String mixedErrorName = "김나Park2";
        String signErrorName = "!~^&";

        //when//then
        assertThat(new Customer(UUID.randomUUID(), koreanName, email)).isNotNull();
        assertThat(new Customer(UUID.randomUUID(), englishName, email)).isNotNull();
        assertThat(new Customer(UUID.randomUUID(), mixedName, email)).isNotNull();
        assertThatThrownBy(() -> new Customer(UUID.randomUUID(), mixedErrorName, email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_NAME_INPUT.getMessage());
        assertThatThrownBy(() -> new Customer(UUID.randomUUID(), signErrorName, email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_NAME_INPUT.getMessage());
    }

    @DisplayName("고객의 이메일은 기본 이메일 형식(@, .)을 가지고 있어야 한다.")
    @Test
    void customerEmailContainsEmailSign() {
        //given
        String name = "김나박이";
        String validEmail = "test@email.com";
        String notContainsSignEmail = "testemail.com";
        String notContainsDotEmail = "test@emailcom";

        //when //then
        assertThat(new Customer(UUID.randomUUID(), name, validEmail)).isNotNull();
        assertThatThrownBy(() -> new Customer(UUID.randomUUID(), name, notContainsSignEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_EMAIL_INPUT.getMessage());
        assertThatThrownBy(() -> new Customer(UUID.randomUUID(), name, notContainsDotEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_EMAIL_INPUT.getMessage());
    }

    @DisplayName("고객을 수정할 수 있다.")
    @Test
    void updateCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testName", "test@mail.com", false);
        UUID customerId = customer.getCustomerId();
        repository.insert(customer);

        Customer updateCustomer = new Customer(customerId, "updateName", "update@mail.com", true);

        //when
        Customer updatedCustomer = repository.update(updateCustomer);

        //then
        assertThat(updatedCustomer).isEqualTo(updateCustomer);
        assertThat(updatedCustomer.getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @DisplayName("고객을 수정하려면 수정하려는 고객의 아이디가 기존의 고객 아이디와 동일해야 한다.")
    @Test
    void updateCustomer2() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testName", "test@mail.com", false);
        UUID customerId = customer.getCustomerId();
        repository.insert(customer);

        Customer updateCustomer = new Customer(UUID.randomUUID(), "updateName", "update@mail.com", true);

        //when //then
        assertThatThrownBy(() -> repository.update(updateCustomer))
                .isInstanceOf(DataAccessException.class)
                .hasMessageContaining(ExceptionMessage.UPDATE_QUERY_FAILED.getMessage());
    }

    @DisplayName("고객 아이디를 활용해 고객을 삭제할 수 있다.")
    @Test
    void deleteCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testName", "test@email.com");
        UUID customerId = customer.getCustomerId();
        repository.insert(customer);

        //when
        repository.delete(customerId);

        //then
        assertThat(repository.findById(customerId)).isEmpty();
    }

    @DisplayName("존재하지 않은 고객 아이디를 통해서는 고객을 삭제할 수 없다.")
    @Test
    void test() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testName", "test@email.com");
        UUID customerId = customer.getCustomerId();

        //when //then
        assertThatThrownBy(() -> repository.delete(customerId))
                .isInstanceOf(DataAccessException.class)
                .hasMessageContaining(ExceptionMessage.DELETE_QUERY_FAILED.getMessage());
    }

    List<Customer> createStubCustomers() {
        Customer customer1 = new Customer(UUID.randomUUID(), "testA", "testA@naver.com", false);
        Customer customer2 = new Customer(UUID.randomUUID(), "testB", "testB@naver.com", true);
        Customer customer3 = new Customer(UUID.randomUUID(), "testC", "testC@naver.com", true);
        return List.of(customer1, customer2, customer3);
    }

    List<Customer> createStubBlackCustomers() {
        Customer customer1 = new Customer(UUID.randomUUID(), "testA", "testA@naver.com", true);
        Customer customer2 = new Customer(UUID.randomUUID(), "testB", "testB@naver.com", true);
        return List.of(customer1, customer2);
    }
}