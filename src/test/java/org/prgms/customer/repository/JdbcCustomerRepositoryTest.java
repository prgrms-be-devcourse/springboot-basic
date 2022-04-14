package org.prgms.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

@SpringJUnitConfig
class CustomerRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = "org.prgms")
    static class Config {
        @Bean
        DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/order_mgmt")
                    .username("test-user")
                    .password("test111!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    private CustomerRepository jdbcCustomerRepository;

    private final Customer newCustomer = new Customer(UUID.randomUUID(), "user-test", "user-test@gmail.com");

    @BeforeAll
    static void setup() {
        var config = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test-user", "test111!")
                .withTimeZone("Asia/Seoul")
                .build();
        var mysqld = anEmbeddedMysql(config)
                .addSchema("order_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @BeforeEach
    void deleteAll() {
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("모두 조회 기능 테스트")
    void findAllTest() {
        jdbcCustomerRepository.insert(newCustomer);
        var anotherCustomer = new Customer(UUID.randomUUID(), "anotherUser", "anonymous@gmail.com");
        jdbcCustomerRepository.insert(anotherCustomer);
        List<Customer> customers = jdbcCustomerRepository.findAll();
        Assertions.assertThat(customers).hasSize(2).contains(anotherCustomer, newCustomer);

    }

    @Test
    @DisplayName("이름으로 조회 테스트")
    void findByNameTest() {
        jdbcCustomerRepository.insert(newCustomer);
        List<Customer> customers = jdbcCustomerRepository.findByName("user-test");
        Assertions.assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("고객 데이터 insert 테스트")
    void inserTest() {
        jdbcCustomerRepository.insert(newCustomer);
        Assertions.assertThat(jdbcCustomerRepository.findByName("user-test").get(0).customerId())
                .isEqualTo(newCustomer.customerId());
    }

    @Test
    @DisplayName("고객 정보 업데이트 테스트")
    public void updateTest() {
        jdbcCustomerRepository.insert(newCustomer);
        Customer updateUser = new Customer(newCustomer.customerId(), "update-user", newCustomer.email());
        jdbcCustomerRepository.update(updateUser);
        Assertions.assertThat(
                jdbcCustomerRepository.findByName("update-user")
                        .get(0).customerId()).isEqualTo(newCustomer.customerId());
    }
}