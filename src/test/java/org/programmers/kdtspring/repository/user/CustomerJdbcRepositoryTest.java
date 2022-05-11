package org.programmers.kdtspring.repository.user;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.kdtspring.entity.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    CustomerJdbcRepository customerJdbcRepository;
    Customer newCustomer;

    @BeforeAll()
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test1-user@naver.com", LocalDateTime.now());
    }

    @AfterEach
    void test() {
        customerJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 추가")
    public void testInsert() {
        customerJdbcRepository.insert(newCustomer);

        Optional<Customer> findCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(findCustomer.isPresent()).isTrue();
    }

    @Test
    @DisplayName("이름으로 고객을 조회")
    public void testFindByName() {
        customerJdbcRepository.insert(newCustomer);

        Optional<Customer> customer = customerJdbcRepository.findByName(newCustomer.getName().getName());

        assertThat(customer.isPresent()).isTrue();
        assertThat(customer.get().getName().getName()).isEqualTo("test-user");
    }

    @Test
    @DisplayName("이메일으로 고객을 조회")
    public void testFindByEmail() {
        customerJdbcRepository.insert(newCustomer);
        Optional<Customer> customer = customerJdbcRepository.findByEmail(newCustomer.getEmail().getEmail());

        assertThat(customer.isPresent()).isTrue();
        assertThat(customer.get().getEmail().getEmail()).isEqualTo("test1-user@naver.com");
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        customerJdbcRepository.insert(newCustomer);

        List<Customer> customers = customerJdbcRepository.findAll();
        assertThat(customers.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Count() 테스트")
    void countTest() {
        customerJdbcRepository.insert(newCustomer);

        assertThat(customerJdbcRepository.count()).isEqualTo(1);
    }

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.kdtspring"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher_mgmt")
                    .username("root")
                    .password("xngosem258!")
                    .type(HikariDataSource.class)
                    .build();

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        NamedParameterJdbcTemplate NamedJdbcParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }
}