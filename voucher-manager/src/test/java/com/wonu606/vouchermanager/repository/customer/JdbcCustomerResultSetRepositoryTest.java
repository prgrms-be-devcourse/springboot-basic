package com.wonu606.vouchermanager.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerResultSet;
import com.wonu606.vouchermanager.domain.customer.EmailAddress;
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
class JdbcCustomerResultSetRepositoryTest {

    private JdbcCustomerResultSetRepository jdbcCustomerResultSetRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        jdbcCustomerResultSetRepository = new JdbcCustomerResultSetRepository(dataSource);
    }

    @Test
    @DisplayName("save_저장되어 있지 않은 Customer면이라면_Customer가 저장된다.")
    void save_UnsavedCustomer_CustomerSaved() {
        // given
        Customer customer = new Customer(
                new EmailAddress("Linlin@onepiece.org"), "Big Mom");

        // when
        jdbcCustomerResultSetRepository.save(customer);
        var foundCustomer = jdbcCustomerResultSetRepository.findByEmailAddress(
                customer.getEmailAddress());

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
                new EmailAddress("Linlin@onepiece.org"), "Big Mom");
        jdbcCustomerResultSetRepository.save(customer);

        // when
        var foundCustomer =
                jdbcCustomerResultSetRepository.findByEmailAddress(customer.getEmailAddress());

        // then
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getEmailAddress()).isEqualTo(customer.getEmailAddress());
    }

    @Test
    @DisplayName("findByEmailAddress_저장되지 않은 Customer_Empty를 반환한다.")
    void findByEmailAddress_UnsavedCustomer_ReturnsEmpty() {
        // given
        String unsavedEmailAddress = "unsavedEmailAddress@domain.com";

        // when
        var foundCustomer =
                jdbcCustomerResultSetRepository.findByEmailAddress(unsavedEmailAddress);

        // then
        assertThat(foundCustomer).isNotPresent();
    }

    @Test
    @DisplayName("findAll_저장된 모든 Customer_저장된 모든 Customer들을 반환한다.")
    void findAll_SavedCustomers_ReturnsAllCustomers() {
        // given
        Customer customer1 = new Customer(
                new EmailAddress("Linlin@onepiece.org"), "Big Mom");
        Customer customer2 = new Customer(
                new EmailAddress("loopy@onepiece.org"), "Pirate King");
        jdbcCustomerResultSetRepository.save(customer1);
        jdbcCustomerResultSetRepository.save(customer2);

        // when
        List<CustomerResultSet> allCustomers = jdbcCustomerResultSetRepository.findAll();

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
                new EmailAddress("Linlin@onepiece.org"), "Big Mom");
        jdbcCustomerResultSetRepository.save(customer);

        // then
        jdbcCustomerResultSetRepository.deleteByEmailAddress(customer.getEmailAddress());
        Optional<CustomerResultSet> foundCustomer = jdbcCustomerResultSetRepository
                .findByEmailAddress(customer.getEmailAddress());

        // when
        assertThat(foundCustomer).isNotPresent();
    }

    @Test
    @DisplayName("deleteAll_저장된 모든 Customer_모든 Customer를 제거한다.")
    void deleteAll_MultipleCustomers_AllCustomersDeleted() {
        // given
        Customer customer1 = new Customer(
                new EmailAddress("Linlin@onepiece.org"), "Big Mom");
        Customer customer2 = new Customer(
                new EmailAddress("loopy@onepiece.org"), "Pirate King");
        jdbcCustomerResultSetRepository.save(customer1);
        jdbcCustomerResultSetRepository.save(customer2);

        // then
        jdbcCustomerResultSetRepository.deleteAll();
        List<CustomerResultSet> allCustomers = jdbcCustomerResultSetRepository.findAll();

        // when
        assertThat(allCustomers).isEmpty();
    }
}
