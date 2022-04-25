package me.programmers.springboot.basic.springbootbasic.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;
import me.programmers.springboot.basic.springbootbasic.customer.model.CustomerInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcTemplateCustomerRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"me.programmers.springboot.basic.springbootbasic.customer"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var datasource =  DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("programmers")
                    .password("programmers")
                    .type(HikariDataSource.class)
                    .build();
            datasource.setMaximumPoolSize(1000);
            datasource.setMinimumIdle(100);
            return datasource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplateCustomerRepository jdbcRepository;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), new CustomerInfo("test-user", "test-user4@gmail.com"), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        var customers = jdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindByName() {
        var customer = jdbcRepository.findByName(newCustomer.getCustomerInfo().getName());
        assertThat(customer.isEmpty(), is(false));

        var unknown = jdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() {
        try {
            jdbcRepository.insert(newCustomer);
        } catch (BadSqlGrammarException e) {
            e.getSQLException().getErrorCode();
        }

        var retrievedCustomer = jdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    public void testFindByEmail() {
        var customer = jdbcRepository.findByEmail(newCustomer.getCustomerInfo().getEmail());
        assertThat(customer.isEmpty(), is(false));

        var unknown = jdbcRepository.findByEmail("unknown-user@gmail.com");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() {
        newCustomer.changeName("updated-user");
        jdbcRepository.update(newCustomer);

        var all = jdbcRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));

        var retrievedCustomer = jdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

}