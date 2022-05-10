package org.programmers.kdtspring.repository.user;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

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

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    Customer newCustomer;

    @BeforeAll()
    void setup() {
        newCustomer = new Customer(1L, "test-user", "test1-user@naver.com", LocalDateTime.now());
        customerJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 추가")
    public void testInsert() {

        customerJdbcRepository.save(newCustomer);

        Optional<Customer> findCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(findCustomer.isEmpty(), is(false));
        assertThat(findCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("이름으로 고객을 조회")
    public void testFindByName() throws InterruptedException {
        customerJdbcRepository.save(newCustomer);
        Optional<Customer> customer = customerJdbcRepository.findByName(newCustomer.getName());
        assertThat(customer.isEmpty(), is(false));

        Optional<Customer> unknown = customerJdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));

    }

    @Test
    @DisplayName("이메일으로 고객을 조회")
    public void testFindByEmail() throws InterruptedException {
        customerJdbcRepository.save(newCustomer);
        Optional<Customer> customer = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        assertThat(customer.isEmpty(), is(false));


        Optional<Customer> unknwon = customerJdbcRepository.findByEmail("unknown-user");
        assertThat(unknwon.isEmpty(), is(true));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        customerJdbcRepository.save(newCustomer);

        List<Customer> customers = customerJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }
}