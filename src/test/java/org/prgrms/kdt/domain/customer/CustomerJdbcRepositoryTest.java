package org.prgrms.kdt.domain.customer;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.common.YamlPropertiesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@SpringJUnitConfig
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerJdbcRepositoryTest {

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;
    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    @BeforeAll
    void setUp() {
        newCustomer = new Customer(new RandomDataGenerator().nextLong(0, 10000),
                Name.valueOf("테스트"),
                Email.valueOf("test@gmail.com"),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        customerJdbcRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() {
        customerJdbcRepository.insert(newCustomer);
        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertAll(
                () -> assertThat(retrievedCustomer.isEmpty(), is(false)),
                () -> assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer, "lastLoginAt"))
        );
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        var customers = customerJdbcRepository.findAll();

        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("고객을 아이디로 조회할 수 있다.")
    public void testFindById() {
        var customer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertThat(customer.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindByName() {
        var customer = customerJdbcRepository.findByName(newCustomer.getName());
        var unknown = customerJdbcRepository.findByName(Name.valueOf("unknown"));

        assertAll(
                () -> assertThat(customer.isEmpty(), is(false)),
                () -> assertThat(unknown.isEmpty(), is(true))
        );

    }

    @Test
    @Order(6)
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    public void testFindByEmail() {
        var customer = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        var unknown = customerJdbcRepository.findByEmail(Email.valueOf("unknown-user@gmail.com"));

        assertAll(
                () -> assertThat(customer.isEmpty(), is(false)),
                () -> assertThat(unknown.isEmpty(), is(true))
        );
    }

    @Test
    @Order(7)
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdate() {
        newCustomer.getName().changeName("updatedName");
        customerJdbcRepository.update(newCustomer);
        var all = customerJdbcRepository.findAll();
        var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertAll(
                () -> assertThat(all, hasSize(1)),
                () -> assertThat(all, everyItem(samePropertyValuesAs(newCustomer, "lastLoginAt"))),
                () -> assertThat(retrievedCustomer.isEmpty(), is(false)),
                () -> assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer, "lastLoginAt"))
        );
    }


    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.domain.customer"}
    )
    static class Config {

        private String user;
        private String password;

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("kdt")
                    .password("kdt")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
}
