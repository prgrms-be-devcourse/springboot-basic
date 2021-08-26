package org.prgrms.kdt.customer;

import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.config.Charset.UTF8;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

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
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }

    @Autowired
    CustomerRepository customerRepository;

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


}