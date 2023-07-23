package com.wonu606.vouchermanager.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.reader.CustomerJdbcReader;
import com.wonu606.vouchermanager.repository.customer.reader.CustomerReader;
import com.wonu606.vouchermanager.repository.customer.reader.rowmapper.CustomerReaderRowMapperManager;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.repository.customer.store.CustomerJdbcStore;
import com.wonu606.vouchermanager.repository.customer.store.CustomerStore;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@DisplayName("JdbcCustomerResultSetRepository 테스트")
class CustomerJdbcRepositoryTest {

    private CustomerJdbcRepository repository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp() {
        CustomerReader reader = new CustomerJdbcReader(namedParameterJdbcTemplate,
                new CustomerReaderRowMapperManager());
        CustomerStore store = new CustomerJdbcStore(namedParameterJdbcTemplate);
        repository = new CustomerJdbcRepository(reader, store);
    }

    @Test
    @DisplayName("save_저장되어 있지 않은 Customer면이라면_Customer가 저장된다.")
    void save_UnsavedCustomer_CustomerSaved() {
        // given
        Customer customer = new Customer(
                new Email("Linlin@onepiece.org"), "Big Mom");

        Email email = new Email(customer.getEmailAddress());

        // when
        repository.insert(
                new CustomerCreateQuery(customer.getEmailAddress(), customer.getNickname()));
        List<CustomerResultSet> actualAllList = repository.findAll();

        // then
        assertThat(actualAllList).isNotNull();
        assertThat(actualAllList).hasSize(1);
        assertThat(actualAllList.get(0).getEmail()).isEqualTo(customer.getEmailAddress());
        assertThat(actualAllList.get(0).getNickname()).isEqualTo(customer.getNickname());
    }

    @Test
    @DisplayName("findAll_저장된 모든 Customer_저장된 모든 Customer들을 반환한다.")
    void findAll_SavedCustomers_ReturnsAllCustomers() {
        // given
        Customer customer1 = new Customer(
                new Email("Linlin@onepiece.org"), "Big Mom");
        Customer customer2 = new Customer(
                new Email("loopy@onepiece.org"), "Pirate King");
        repository.insert(
                new CustomerCreateQuery(customer1.getEmailAddress(), customer1.getNickname()));
        repository.insert(
                new CustomerCreateQuery(customer2.getEmailAddress(), customer2.getNickname()));

        // when
        List<CustomerResultSet> allCustomers = repository.findAll();

        // then
        assertThat(allCustomers).hasSize(2);
        assertThat(allCustomers).extracting("email")
                .contains(customer1.getEmailAddress(), customer2.getEmailAddress());
        assertThat(allCustomers).extracting("nickname")
                .contains(customer1.getNickname(), customer2.getNickname());
    }

    @Test
    @DisplayName("deleteByCustomerId_저장된 Customer_Customer를 제거한다.")
    void deleteByCustomerId_SavedCustomer_CustomerDeleted() {
        // given
        Customer customer = new Customer(
                new Email("Linlin@onepiece.org"), "Big Mom");
        repository.insert(
                new CustomerCreateQuery(customer.getEmailAddress(), customer.getNickname()));

        Email email = new Email(customer.getEmailAddress());

        // then
        repository.deleteByCustomerId(customer.getEmailAddress());
        List<CustomerResultSet> actualAllList = repository.findAll();

        // when
        assertThat(actualAllList).hasSize(0);
    }
}
