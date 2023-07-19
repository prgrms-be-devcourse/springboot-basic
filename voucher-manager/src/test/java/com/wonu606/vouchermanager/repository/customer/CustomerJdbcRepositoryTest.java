package com.wonu606.vouchermanager.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
@DisplayName("JdbcCustomerResultSetRepository 테스트")
class CustomerJdbcRepositoryTest {

    private CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        customerJdbcRepository = new CustomerJdbcRepository(dataSource);
    }

    @Test
    @DisplayName("save_저장되어 있지 않은 Customer면이라면_Customer가 저장된다.")
    void save_UnsavedCustomer_CustomerSaved() {
        // given
        Customer customer = new Customer(
                new Email("Linlin@onepiece.org"), "Big Mom");

        Email email = new Email(customer.getEmailAddress());

        // when
        customerJdbcRepository.save(customer);
        var foundCustomer = customerJdbcRepository.findByEmailAddress(
                email);

        // then
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getEmailAddress()).isEqualTo(customer.getEmailAddress());
        assertThat(foundCustomer.get().getNickname()).isEqualTo(customer.getNickname());
    }

    @Test
    @DisplayName("findByEmailAddress_저장된 Customer라면_Customer가 반환된다.")
    void findByEmailAddress_CustomerPresent_ReturnsSameCustomer() {
        // given
        Customer customer = new Customer(
                new Email("Linlin@onepiece.org"), "Big Mom");
        customerJdbcRepository.save(customer);

        Email email = new Email(customer.getEmailAddress());

        // when
        var foundCustomer =
                customerJdbcRepository.findByEmailAddress(email);

        // then
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getEmailAddress()).isEqualTo(customer.getEmailAddress());
    }

    @Test
    @DisplayName("findByEmailAddress_저장되지 않은 Customer_Empty를 반환한다.")
    void findByEmailAddress_UnsavedCustomer_ReturnsEmpty() {
        // given
        Email unsavedEmail = new Email("unsavedEmailAddress@domain.com");

        // when
        var foundCustomer =
                customerJdbcRepository.findByEmailAddress(unsavedEmail);

        // then
        assertThat(foundCustomer).isNotPresent();
    }

    @Test
    @DisplayName("findAll_저장된 모든 Customer_저장된 모든 Customer들을 반환한다.")
    void findAll_SavedCustomers_ReturnsAllCustomers() {
        // given
        Customer customer1 = new Customer(
                new Email("Linlin@onepiece.org"), "Big Mom");
        Customer customer2 = new Customer(
                new Email("loopy@onepiece.org"), "Pirate King");
        customerJdbcRepository.save(customer1);
        customerJdbcRepository.save(customer2);

        // when
        List<CustomerResultSet> allCustomers = customerJdbcRepository.findAll();

        // then
        assertThat(allCustomers).hasSize(2);
        assertThat(allCustomers).extracting("emailAddress")
                .contains(customer1.getEmailAddress(), customer2.getEmailAddress());
    }

    @Test
    @DisplayName("deleteByEmailAddress_저장된 Customer_Customer를 제거한다.")
    void deleteByEmailAddress_SavedCustomer_CustomerDeleted() {
        // given
        Customer customer = new Customer(
                new Email("Linlin@onepiece.org"), "Big Mom");
        customerJdbcRepository.save(customer);

        Email email = new Email(customer.getEmailAddress());

        // then
        customerJdbcRepository.deleteByEmailAddress(email);
        Optional<CustomerResultSet> foundCustomer = customerJdbcRepository
                .findByEmailAddress(email);

        // when
        assertThat(foundCustomer).isNotPresent();
    }

    @Test
    @DisplayName("deleteAll_저장된 모든 Customer_모든 Customer를 제거한다.")
    void deleteAll_MultipleCustomers_AllCustomersDeleted() {
        // given
        Customer customer1 = new Customer(
                new Email("Linlin@onepiece.org"), "Big Mom");
        Customer customer2 = new Customer(
                new Email("loopy@onepiece.org"), "Pirate King");
        customerJdbcRepository.save(customer1);
        customerJdbcRepository.save(customer2);

        // then
        customerJdbcRepository.deleteAll();
        List<CustomerResultSet> allCustomers = customerJdbcRepository.findAll();

        // when
        assertThat(allCustomers).isEmpty();
    }
}
