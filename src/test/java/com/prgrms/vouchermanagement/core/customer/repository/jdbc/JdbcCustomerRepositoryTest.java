package com.prgrms.vouchermanagement.core.customer.repository.jdbc;

import com.prgrms.vouchermanagement.core.customer.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("prod")
@ContextConfiguration(classes = {JdbcCustomerRepository.class, JdbcCustomerRepositoryTest.Config.class})
class JdbcCustomerRepositoryTest {

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .addScript("sql/customer-init.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    private JdbcCustomerRepository jdbcCustomerRepository;

    @BeforeEach
    public void cleanUp() {
        jdbcCustomerRepository.deleteAll();
    }

    @DisplayName("Customer를 저장할 수 있다.")
    @Test
    void testSave() {
        // given
        Customer customer = new Customer(UUID.randomUUID().toString(), "sujin", "sujin@email.com");

        // when
        jdbcCustomerRepository.save(customer);
        List<Customer> customerList = jdbcCustomerRepository.findAll();

        // then
        assertThat(customerList.isEmpty(), is(false));
    }

    @DisplayName("id 값으로 Customer를 조회할 수 있다.")
    @Test
    void testFindById() {
        // given
        Customer customer = new Customer(UUID.randomUUID().toString(), "sujin", "sujin@email.com");
        jdbcCustomerRepository.save(customer);

        // when
        Optional<Customer> findCustomer = jdbcCustomerRepository.findById(customer.getId());

        // then
        assertThat(findCustomer.get().equals(customer), is(true));
    }



    @DisplayName("Customer를 전체 조회할 수 있다.")
    @Test
    void testFindAll() {

        // given
        Customer customer1 = new Customer(UUID.randomUUID().toString(), "customer1", "customer1@email.com");
        Customer customer2 = new Customer(UUID.randomUUID().toString(), "customer2", "customer2@email.com");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        // when
        List<Customer> customerList = jdbcCustomerRepository.findAll();

        // then
        assertThat(customerList.size(), is(2));

    }

    @DisplayName("id 값으로 Customer를 삭제할 수 있다.")
    @Test
    void testDeleteById() {
        // given
        Customer customer = new Customer(UUID.randomUUID().toString(), "customer", "customer@email.com");

        jdbcCustomerRepository.save(customer);

        // when
        jdbcCustomerRepository.deleteById(customer.getId());

        List<Customer> customerList = jdbcCustomerRepository.findAll();

        int a = 1;

        // then
        assertThat(customerList.contains(customer), is(false));
    }

    @DisplayName("전체 Customer를 삭제할 수 있다.")
    @Test
    void testDeleteAll() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID().toString(), "customer1", "customer1@email.com");
        Customer customer2 = new Customer(UUID.randomUUID().toString(), "customer2", "customer2@email.com");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        // when
        jdbcCustomerRepository.deleteAll();

        List<Customer> customerList = jdbcCustomerRepository.findAll();

        // then
        assertThat(customerList.size(), is(0));
    }

    @DisplayName("id 값들에 해당하는 Customer를 모두 조회할 수 있다.")
    @Test
    void testFindAllByIds() {
        // given
        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();
        String uuid3 = UUID.randomUUID().toString();
        Customer customer1 = new Customer(uuid1, "customer1", "customer1@email.com");
        Customer customer2 = new Customer(uuid2, "customer2", "customer2@email.com");
        Customer customer3 = new Customer(uuid3, "customer3", "customer3@email.com");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);
        jdbcCustomerRepository.save(customer3);

        // when
        List<Customer> voucherList = jdbcCustomerRepository.findAllByIds(List.of(uuid1, uuid2));

        // then
        assertThat(voucherList.containsAll(List.of(customer1, customer2)), is(true));
    }

}
