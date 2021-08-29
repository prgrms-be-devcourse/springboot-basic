package org.prgrms.kdt.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerNamedJdbcTemplateRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = "org.prgrms.kdt.customer"
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("admin")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
            return new NamedParameterJdbcTemplate(dataSource());
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        public TransactionTemplate transactionTemplate() {
            return new TransactionTemplate(platformTransactionManager());
        }
    }

    @Autowired
    CustomerNamedJdbcRepository customerRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        customerRepository.deleteAll();
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user125553@gmail.com", LocalDateTime.now());
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다")
    public void insert() {

        // given
        customerRepository.insert(newCustomer);

        // when
        Optional<Customer> retrievedCustomer = customerRepository.findById(newCustomer.getCustomerId());

        // then
        assertThat(retrievedCustomer.isEmpty()).isFalse();
        assertThat(retrievedCustomer.get())
                .extracting(Customer::getCustomerId, Customer::getName, Customer::getEmail)
                .containsExactly(newCustomer.getCustomerId(), newCustomer.getName(), newCustomer.getEmail());
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다")
    public void findAll() throws InterruptedException {
        // given
        List<Customer> customers = customerRepository.findAll();
        // when

        // then
        assertThat(customers.isEmpty()).isFalse();

    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다")
    public void findByName() {
        // given
        Optional<Customer> customer = customerRepository.findByName(newCustomer.getName());

        // when

        // then
        assertThat(customer.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다")
    public void findByEmail() {
        // given
        Optional<Customer> customer = customerRepository.findByEmail(newCustomer.getEmail());
        // when

        // then
        assertThat(customer.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    public void updateCustomer() {
        // given
        newCustomer.changeName("updated-user");
        // when
        customerRepository.update(newCustomer);

        // then
        List<Customer> all = customerRepository.findAll();

        assertThat(all.isEmpty()).isFalse();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0))
                .extracting(Customer::getCustomerId, Customer::getName, Customer::getEmail)
                .containsExactly(newCustomer.getCustomerId(), newCustomer.getName(), newCustomer.getEmail());


        Optional<Customer> retrievedCustomer = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty()).isFalse();
        assertThat(retrievedCustomer.get())
                .extracting(Customer::getCustomerId, Customer::getName, Customer::getEmail)
                .containsExactly(newCustomer.getCustomerId(), newCustomer.getName(), newCustomer.getEmail());

    }

    @Test
    @DisplayName("트랜잭션 테스트")
    public void testTransaction() throws Exception {
//        Optional<Customer> customer = customerRepository.findById(newCustomer.getCustomerId());
//        assertThat(customer).isPresent();
//        Customer one = new Customer(UUID.randomUUID(), "a", "a@gmail.com", LocalDateTime.now());
//        Customer insertedOne = customerRepository.insert(one);
//        try {
//            customerRepository.testTransaction(new Customer(insertedOne.getCustomerId(), "b", customer.get().getEmail(), one.getCreatedAt()));
//        } catch (DataAccessException e) {
//
//        }
//
//        Optional<Customer> maybeNewOne = customerRepository.findById(insertedOne.getCustomerId());
//        assertThat(maybeNewOne).isPresent();
//        assertThat(maybeNewOne.get())
//                .extracting(Customer::getCustomerId, Customer::getName, Customer::getEmail)
//                .containsExactly(one.getCustomerId(), one.getName(), one.getEmail());

    }




}