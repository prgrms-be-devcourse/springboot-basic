package com.prgrms.repository.customer;

import com.prgrms.model.customer.Customer;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {


    @Configuration
    @ComponentScan(
            basePackages = {"com.prgrms.repository.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_byeol")
                    .username("root")
                    .password("7351")
                    .type(HikariDataSource.class)
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

    }

    @Autowired
    JdbcCustomerRepository customerNamedJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    private int id = 1;

    @BeforeAll
    void clean() {
        newCustomer = new Customer(id, "test-user", "test1-user@gmail.com", LocalDateTime.now());
        customerNamedJdbcRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가한 결과 반환하는 고객의 아이디와 추가한 고객의 아이디는 같다.")
    public void insert_CustomerId_EqualsNewCustomerId() {

        customerNamedJdbcRepository.insert(newCustomer);

        var retrievedCustomer = customerNamedJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get().getCustomerId(), samePropertyValuesAs(newCustomer.getCustomerId()));

    }

    @Test
    @Order(3)
    @DisplayName("데이터베이스에 몇 개의 데이터를 저장한 후 전체 고객을 조회한 결과는 빈 값을 반환하지 않는다.")
    public void findAll_Customer_NotEmpty() {
        var customers = customerNamedJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("데이터베이스에 존재하는 회원의 이름으로 검색했을 때 빈값을 반환하지 않는다.")
    public void findByName_ExistingCustomer_NotEmpty() {
        var customers = customerNamedJdbcRepository.findByName(newCustomer.getName());
        assertThat(customers.isEmpty(), is(false));

        var unknownCustomers = customerNamedJdbcRepository.findByName("테스터");
        assertThat(unknownCustomers.isEmpty(), is(true));

    }

    @Test
    @Order(5)
    @DisplayName("데이터베이스에 존재하는 회원의 이메일로 검색했을 때 빈값을 반환하지 않는다.")
    public void findByEmail_ExistingCustomerEmail_NotEmpty() {
        var customers = customerNamedJdbcRepository.findByEmail(newCustomer.getEmail());
        assertThat(customers.isEmpty(), is(false));

        var unknownCustomers = customerNamedJdbcRepository.findByEmail("테스터");
        assertThat(unknownCustomers.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("고객의 이름을 수정하고 데이터 베이스에 넣고 이를 다시 검색했을 때 수정된 이름으로 반환한다.")
    public void update_Name_EqualsExistingCustomerName() {
        newCustomer.changeName("updated-user");
        customerNamedJdbcRepository.update(newCustomer);

        var all = customerNamedJdbcRepository.findAll();
        assertThat(all, hasSize(1));

        var retrievedCustomer = customerNamedJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get().getName(), samePropertyValuesAs(newCustomer.getName()));
    }
}
