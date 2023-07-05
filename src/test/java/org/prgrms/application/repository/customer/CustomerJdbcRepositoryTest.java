package org.prgrms.application.repository.customer;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.application.domain.customer.Customer;
import org.prgrms.application.repository.exception.BadSqlExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(BadSqlExceptionHandler.class)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.application.domain.customer", "org.prgrms.application.repository"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(EmbeddedDatabaseType.H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .addScript("schema.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }}

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(1L, "test-user", "test-user@gmail.com",
                LocalDateTime.now(),LocalDateTime.now());
        //    customerJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() {
        customerJdbcRepository.insert(newCustomer);

        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        List<Customer> customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindByName() {
        Optional<Customer> customer = customerJdbcRepository.findByName("kim");
        assertThat(customer.isEmpty(), is(false));

        Optional<Customer> unknown = customerJdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    public void testFindByEmail() {
        Optional<Customer> customer = customerJdbcRepository.findByEmail("kim@gmail.com");
        assertThat(customer.isEmpty(), is(false));

        Optional<Customer> unknown = customerJdbcRepository.findByEmail("unknown-user@gmail.com");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() {
        Optional<Customer> customer = customerJdbcRepository.findById(10L);
        Customer notNullCustomer = customer.orElse(null);
        notNullCustomer.changeName("updated-user");
        customerJdbcRepository.update(notNullCustomer);

        List<Customer> customers = customerJdbcRepository.findAll();
        assertThat(customers, hasSize(1));
        assertThat(customers, everyItem(samePropertyValuesAs(notNullCustomer)));

        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(notNullCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(notNullCustomer));
    }

    @Test
    @DisplayName("고객을 전체 삭제할 수 있다.")
    public void testDelete() {
        customerJdbcRepository.deleteAll();
        List<Customer> customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(true));
    }

}